package com.weibangong.framework.auth.interceptor;

import com.weibangong.framework.auth.interceptor.exception.SecurityErrorMsg;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import com.weibangong.framework.auth.interceptor.exception.ServiceException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import java.lang.reflect.Method;

@Slf4j
public class AuthInInterceptor extends AbstractPhaseInterceptor<Message> {

    public static final String USER_AGENT = "User-Agent";

    public static final String BEARER_SCHEME = "Bearer";

    private static String base64KeyStr = null;

    public static final String AID = "aid";

    public static final String TENANT_ID = "tid";

    public static final String ACCESS_TOKEN = "access_token";

    public AuthInInterceptor() {
        super(Phase.USER_LOGICAL);
        base64KeyStr = System.getProperty("wbg.token");
    }

    @Override
    public void handleMessage(Message message) throws Fault {

        if (base64KeyStr == null || base64KeyStr.trim().isEmpty()) return;
        Method method = (Method) message.get("org.apache.cxf.resource.method");

        RequestAuthentication requestAuthentication = method.getAnnotation(RequestAuthentication.class);
        if (requestAuthentication == null) {
            return;
        }
        HttpServletRequest request = (HttpServletRequest) message.get("HTTP.REQUEST");

        String userAgent = request.getHeader(USER_AGENT);

        String token = getTokenFromHeader(request);
        if (StringUtils.isBlank(token)) {
            token = getTokenFromCookie(request);
        }

        Client context = parseToken(token, userAgent);

        if (context == null) {
            throw new ServiceException(SecurityErrorMsg.SECURITY_TOKEN_ANALYSIS_ERROR);
        }

        if (requestAuthentication.token() && (context.getRunasId() == null || context.getTenantId() == null)) {
            throw new ServiceException(SecurityErrorMsg.SECURITY_TOKEN_INVALID);
        }

        AuthContext.put(context);
    }

    /**
     * 从请求头获取token
     *
     * @param request http request
     * @return
     */
    private String getTokenFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotBlank(authorization)) {
            // 获取token
            String[] authTokens = authorization.split(" ");
            if (authTokens != null && authTokens.length > 1) {
                String scheme = authTokens[0];
                String token = authTokens[1];
                if (BEARER_SCHEME.equals(scheme)) {
                    return token;
                }
            }
        }

        return "";
    }

    /**
     * 从请求cookie获取token
     *
     * @param request http request
     * @return
     */
    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";

        if (cookies == null) {
            return token;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(ACCESS_TOKEN)) {
                token = cookie.getValue();
                break;
            }
        }
        return token;
    }

    /**
     * 解析token
     */
    private Client parseToken(String token, String userAgent) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(base64KeyStr).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }

        Client client = new Client();
        long clientId = Long.parseLong(claims.getId());
        long loginId = Long.parseLong(claims.getSubject());

        client.setLoginId(loginId);

        Object aId = claims.get(AID);
        Object tId = claims.get(TENANT_ID);

        if (aId != null && tId != null) {
            // 登录返回的token
            client.setRunasId(Long.parseLong(String.valueOf(aId)));
            client.setTenantId(Long.parseLong(String.valueOf(tId)));
        }

        parseUserAgent(userAgent, client);

        return client;
    }

    private void parseUserAgent(String userAgent, Client client) {
        if (userAgent != null && userAgent.startsWith("haizhiOA;")) {
            String[] segments = userAgent.split(";");
            if (segments.length >= 5) {
                client.setAppName(segments[0]);
                client.setAppVersion(segments[1]);
                client.setSystemType(parseSystemType(segments[2]));
                client.setSystemVersion(segments[3]);
                String channelStr = segments[4];
                client.setChannel(getChannel(channelStr));
            }
            if (segments.length >= 6) {
                try {
                    client.setClientId(Long.parseLong(segments[5]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Client.SystemType parseSystemType(String systemName) {
        if (systemName == null) {
            return null;
        }
        if ("iphone".equalsIgnoreCase(systemName) || "ipad".equalsIgnoreCase(systemName)) {
            return Client.SystemType.IOS;
        }
        try {
            return Client.SystemType.valueOf(systemName.toUpperCase());
        } catch (Throwable ex) {
            log.error("unsupport client type, Error happened when parse client system type.", ex);
            return Client.SystemType.WEB;
        }
    }

    public static int getChannel(String name) {
        if ("default".equalsIgnoreCase(name) || "1dev".equalsIgnoreCase(name)) {
            return 0;
        } else if ("appstore".equalsIgnoreCase(name) || "2wbg".equalsIgnoreCase(name)) {
            return 1;
        } else if ("5tt".equalsIgnoreCase(name)) {
            return 2;
        } else if ("4tt".equalsIgnoreCase(name)) {
            return 3;
        } else if ("6rt".equalsIgnoreCase(name)) {
            return 4;
        } else if ("3rt".equalsIgnoreCase(name)) {
            return 5;
        }

        return 0;
    }
}
