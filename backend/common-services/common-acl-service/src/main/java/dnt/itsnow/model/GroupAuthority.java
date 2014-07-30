/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.model;

import org.springframework.security.core.GrantedAuthority;

/** Description */
public class GroupAuthority implements GrantedAuthority {

    private String groupName;
    private String authority;

    public String getGroupName() {
        return groupName;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
