/**
 * Developer: Kadvin Date: 14-9-19 下午12:56
 */
package dnt.itsnow.util;

import org.springframework.security.web.csrf.CsrfToken;

/**
 * <h1> the MSC csrf token bean</h1>
 */
public class ItsnowCsrfToken implements CsrfToken {
    private String headerName;
    private String parameterName;
    private String token;

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
