package com.weibangong.mybatis.blueprint.codec;

import com.weibangong.mybatis.blueprint.SqlSessionFactoryBean;
import com.weibangong.mybatis.blueprint.mappers.SecureKeyMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import com.weibangong.mybatis.blueprint.MapperFactory;
import com.weibangong.mybatis.blueprint.model.SecureKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jianghailong on 16/1/9.
 */
@Slf4j
@NoArgsConstructor
public class EncryptionService {

    public static final int KEY_LENGTH = 128;

    private static final String KEY_ALGORITHM = "DES";

    private static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    private SecureRandom random = new SecureRandom();

    private DataSource dataSource;
    private SqlSessionFactoryBean sessionFactory;
    private SecureKeyMapper secureKeyMapper;

    public EncryptionService(DataSource dataSource) {
        this.dataSource = dataSource;

        sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setClassLoader(SecureKeyMapper.class.getClassLoader());
        Set<String> mappers = new HashSet<>();
        mappers.add(SecureKeyMapper.class.getName());
        sessionFactory.setMappers(mappers);

        secureKeyMapper = MapperFactory.get(sessionFactory, SecureKeyMapper.class);
    }

    public String encrypt(String message, String key) throws Exception {
        SecretKey secretKey = secretKey(key);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);

        byte[] buffer = cipher.doFinal(message.getBytes("UTF-8"));
        return new String(Base64.encodeBase64(buffer));
    }

    public String decrypt(String encryptedText, String key) throws Exception {
        byte[] message = Base64.decodeBase64(encryptedText.getBytes("UTF-8"));

        SecretKey secretKey = secretKey(key);
        Cipher decipher = Cipher.getInstance(CIPHER_ALGORITHM);
        decipher.init(Cipher.DECRYPT_MODE, secretKey, random);

        return new String(decipher.doFinal(message), "UTF-8");
    }

    private Map<String, SecureKey> cachedSecretKeys = new HashMap<>();

    //    public SecureKey getSecretKey(Long keyIndex, String action) {
//        String cachedKey = action + "-" + keyIndex;
//        SecureKey secureKey = cachedSecretKeys.get(cachedKey);
//        if (secureKey != null) {
//            return secureKey;
//        }
//
//        long startTime = System.currentTimeMillis();
//        secureKey = secureKeyMapper.selectByKeyIndexAndAction(keyIndex, action);
//        if (secureKey == null) {
//            secureKey = generatedSecretKey(secureKeyMapper, keyIndex, action);
//            long diff = System.currentTimeMillis() - startTime;
//            if (diff > 20) {
//                log.info("generated secureKey {}ms", diff);
//            }
//        } else {
//            cachedSecretKeys.put(cachedKey, secureKey);
//            long diff = System.currentTimeMillis() - startTime;
//            if (diff > 20) {
//                log.debug("select secureKey {}ms", diff);
//            }
//        }
//        return secureKey;
//    }
    public SecureKey getSecretKey(Long keyIndex) {
        String cachedKey = keyIndex + "";
        SecureKey secureKey = cachedSecretKeys.get(cachedKey);
        if (secureKey != null) {
            return secureKey;
        }

        long startTime = System.currentTimeMillis();
        secureKey = secureKeyMapper.selectByKeyIndex(keyIndex);
        if (secureKey == null) {
            secureKey = generatedSecretKey(secureKeyMapper, keyIndex);
            long diff = System.currentTimeMillis() - startTime;
            if (diff > 20) {
                log.info("generated secureKey {}ms", diff);
            }
        } else {
            cachedSecretKeys.put(cachedKey, secureKey);
            long diff = System.currentTimeMillis() - startTime;
            if (diff > 20) {
                log.debug("select secureKey {}ms", diff);
            }
        }
        return secureKey;
    }

    private Lock lock = new ReentrantLock();
//    private SecureKey generatedSecretKey(SecureKeyMapper mapper, Long keyIndex, String action) {
//        SecureKey secureKey;
//        try {
//            lock.lock();
//            String cachedKey = action + "-" + keyIndex;
//            secureKey = cachedSecretKeys.get(cachedKey);
//            if (secureKey != null) {
//                return secureKey;
//            }
//            String generated = Base64.encodeBase64String(
//                    random.generateSeed(KEY_LENGTH)
//            );
//            secureKey = new SecureKey(keyIndex, action, generated);
//            cachedSecretKeys.put(cachedKey, secureKey);
//            mapper.insert(secureKey);
//        } finally {
//            lock.unlock();
//        }
//        return secureKey;
//    }

    private SecureKey generatedSecretKey(SecureKeyMapper mapper, Long keyIndex) {
        SecureKey secureKey;
        try {
            lock.lock();
            String cachedKey = keyIndex + "";
            secureKey = cachedSecretKeys.get(cachedKey);
            if (secureKey != null) {
                return secureKey;
            }
            String generated = Base64.encodeBase64String(
                    random.generateSeed(KEY_LENGTH)
            );
            secureKey = new SecureKey(keyIndex, generated);
            cachedSecretKeys.put(cachedKey, secureKey);
            mapper.insert(secureKey);
        } finally {
            lock.unlock();
        }
        return secureKey;
    }

    private SecretKey secretKey(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException, InvalidKeyException {
        DESKeySpec spec = new DESKeySpec(key.getBytes("UTF-8"), KEY_LENGTH);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return secretKeyFactory.generateSecret(spec);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) throws Exception {
        SecureRandom random = new SecureRandom();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            String generated = Base64.encodeBase64String(
                    random.generateSeed(KEY_LENGTH)
            );
            System.out.println(generated);
        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
