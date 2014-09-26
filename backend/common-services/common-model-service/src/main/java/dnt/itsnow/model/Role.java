/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;
import sun.security.krb5.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>角色对象</h1>
 * <p/>
 */
public class Role extends ConfigItem {

    private String sn;

    private List<RoleDetail> details;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<RoleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<RoleDetail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Role{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", details=").append(details);
        sb.append('}');
        return sb.toString();
    }
}
