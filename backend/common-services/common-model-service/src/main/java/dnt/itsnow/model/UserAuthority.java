/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * the authority granted to user
 */
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserAuthority{");
        sb.append("username='").append(username).append('\'');
        sb.append(", authority='").append(authority).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
