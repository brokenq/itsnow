/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

import java.util.List;

/**
 * <h1>角色对象</h1>
 * <p/>
 */
public class GeneralRole extends Role {

    public List<GeneralRoleDetail> details;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GeneralRole{");
        sb.append("details=").append(details);
        sb.append('}');
        return sb.toString();
    }

    public List<GeneralRoleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<GeneralRoleDetail> details) {
        this.details = details;
    }
}
