package com.weibangong.mybatis.blueprint.interceptor;

import com.weibangong.mybatis.blueprint.annotations.Foretaste;
import com.weibangong.mybatis.blueprint.annotations.KeyIndex;
import com.weibangong.mybatis.blueprint.codec.EncryptionService;
import com.weibangong.mybatis.blueprint.model.SecureKey;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import com.weibangong.mybatis.blueprint.annotations.Encryption;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by jianghailong on 16/1/8.
 */
@Slf4j
@Intercepts({
        @Signature(
                type = Executor.class, method = "query",
                args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
        @Signature(
                type = Executor.class, method = "update",
                args = { MappedStatement.class, Object.class }
        )
})
public class SecretInterceptor implements Interceptor {

    private static final String PREFIX = "$$ENCRYPTION_1.0$$";

    private Set<Class<?>> parsedClass = new HashSet<>();

    private Map<Class<?>, Set<String>> encryptionFields = new HashMap<>();

    private Map<Class<?>, String> encryptionIndexes = new HashMap<>();

    private Map<Class<?>, String> encryptionForetaste = new HashMap<>();

    @Getter
    private DataSource dataSource;

    private EncryptionService encryptionService;

    @Getter @Setter
    private String filter = "com.weibangong.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameter = args[1];

        Class<?> type = statement.getParameterMap().getType();
        parserAnnotation(type);

        List<ParameterMapping> mappings = statement.getBoundSql(parameter).getParameterMappings();
        String encryptSwitch = System.getProperty("com.weibangong.mybatis.blueprint.interceptor.encrypt.switch");
        String encryptTenants = System.getProperty("com.weibangong.mybatis.blueprint.interceptor.encrypt.tenants");
        if ("update".equals(invocation.getMethod().getName())
                && (encryptSwitch != null && "on".equals(encryptSwitch))) {
            String foreTasteField = encryptionForetaste.get(type);
            if (foreTasteField != null && encryptTenants != null) {
                String foretaste = (String) Ognl.getValue(foreTasteField, parameter, String.class);
                if (foretaste != null && ("all".equals(encryptTenants) || encryptTenants.contains(foretaste))) {
                    String keyIndexField = encryptionIndexes.get(type);
                    if (keyIndexField != null) {
                        Long keyIndex = (Long) Ognl.getValue(keyIndexField, parameter, Long.class);
                        if (keyIndex != null) {
                            Set<String> fields = encryptionFields.get(type);
                            for (ParameterMapping mapping : mappings) {
                                String property = mapping.getProperty();
                                if (fields.contains(property)) {
                                    String value = (String) Ognl.getValue(property, parameter, String.class);
                                    if (value == null) {
                                        continue;
                                    }
                                    try {
                                        SecureKey secureKey = encryptionService.getSecretKey(keyIndex);
                                        StringBuffer buffer = new StringBuffer();
                                        buffer.append(PREFIX);
                                        buffer.append(keyIndex).append("$$");
                                        buffer.append(encryptionService.encrypt(value, secureKey.getSecretKey()));
                                        Ognl.setValue(property, parameter, buffer.toString());
                                    } catch (Throwable e) {
                                        log.error("加密失败.", e);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Object result = invocation.proceed();

        if ("query".equals(invocation.getMethod().getName())) {
            if (result instanceof Collection) {
                for (Object item : (Collection) result) {
                    decrypt(item);
                }
            } else {
                decrypt(result);
            }
        } else if ("update".equals(invocation.getMethod().getName())) {
            if (parameter instanceof Collection) {
                for (Object item : (Collection) parameter) {
                    decrypt(item);
                }
            } else {
                decrypt(parameter);
            }
        }
        return result;
    }

    private void encrypt(Object item) {
        try {
            parserAnnotation(item.getClass());
        } catch (Throwable e) {
            log.error("加密失败.", e);
            e.printStackTrace();
        }
    }

    private void decrypt(Object item) {
        try {
            if (item == null) {
                return;
            }
            parserAnnotation(item.getClass());

            Set<String> fields = encryptionFields.get(item.getClass());
            if (fields == null) {
                return;
            }
            for (String field : fields) {
                String value = (String) Ognl.getValue(field, item, String.class);
                if (value == null || value.isEmpty()) {
                    continue;
                }
                if (value.startsWith(PREFIX)) {
                    Integer offset = PREFIX.length();
                    Long keyIndex = Long.parseLong(value.substring(offset, value.indexOf("$$", offset)));
                    SecureKey secureKey = encryptionService.getSecretKey(keyIndex);
                    value = encryptionService.decrypt(
                            value.substring(value.indexOf("$$", offset) + 2), secureKey.getSecretKey());
                    Ognl.setValue(field, item, value);
                }
            }
        } catch (Throwable e) {
            log.error("解密失败.", e);
        }
    }

    private Pattern filterPattern;
    private boolean filtered(String className) {
        if (filter != null && filterPattern == null) {
            filterPattern = Pattern.compile(filter);
        }
        return filterPattern.matcher(className).matches();
    }

    private void parserAnnotation(Class<?> targetType) {
        if (targetType == null || parsedClass.contains(targetType)) {
            return;
        }
        parsedClass.add(targetType);
        if (!filtered(targetType.getName())) {
            return;
        }

        for (Field field : targetType.getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(Foretaste.class);
            if (annotation != null) {
                encryptionForetaste.put(targetType, field.getName());
            }
        }

        for (Field field : targetType.getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(KeyIndex.class);
            if (annotation != null) {
                encryptionIndexes.put(targetType, field.getName());
            }
        }

        Set<String> fields = new HashSet<>();
        for (Field field : targetType.getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(Encryption.class);
            if (annotation != null) {
                fields.add(field.getName());
            }
        }
        encryptionFields.put(targetType, fields);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        if (dataSource != null) {
            encryptionService = new EncryptionService(dataSource);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
