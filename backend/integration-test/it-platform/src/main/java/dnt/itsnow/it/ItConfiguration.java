/**
 * xiongjie on 14-8-11.
 */
package dnt.itsnow.it;

import dnt.itsnow.model.CsrfToken;
import org.springframework.http.HttpHeaders;

/**
 * <h1>Class Usage</h1>
 */
public class ItConfiguration {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /* 配置信息 */
    ////////////////////////////////////////////////////////////////////////////////////////////////

    // 被测试系统
    private String host;
    private int port;

    // 需要记录用户时的信息
    private String username;
    private String password;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /* 运行信息 */
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private String sessionCookies;// 需要记录session时的信息
    private boolean logined;
    private CsrfToken csrf;


    public String getHost() {
        return host;
    }

    public ItConfiguration host(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public ItConfiguration port(int port) {
        this.port = port;
        return this;
    }

    public String getSessionCookies() {
        return sessionCookies;
    }

    public ItConfiguration sessionCookies(String sessionCookies) {
        this.sessionCookies = sessionCookies;
        return this;
    }

    public CsrfToken getCsrf() {
        return csrf;
    }

    public ItConfiguration csrf(CsrfToken csrf) {
        this.csrf = csrf;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ItConfiguration username(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ItConfiguration password(String password) {
        this.password = password;
        return this;
    }

    public boolean hasSessionCookies() {
        return this.sessionCookies != null;
    }

    public HttpHeaders requestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(csrf.getHeaderName(), csrf.getToken());
        headers.set("Cookie", sessionCookies);
        return headers;
    }

    public boolean isLogined() {
        return logined;
    }

    public void logined() {
        this.logined = true;
    }
}
