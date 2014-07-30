/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.model;

import org.springframework.security.core.GrantedAuthority;

/** Description */
public class UserAuthority implements GrantedAuthority {
    private String username;
    private String authority;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
