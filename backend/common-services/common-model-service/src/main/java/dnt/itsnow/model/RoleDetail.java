package dnt.itsnow.model;

/**
 * <h1>类功能说明</h1>
 */
public class RoleDetail extends Group {

    public String accountName;
    public String username;
    public String deptName;
    public String siteName;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GeneralRoleDetail{");
        sb.append("accountName='").append(accountName).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", deptName='").append(deptName).append('\'');
        sb.append(", siteName='").append(siteName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }
}
