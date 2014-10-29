package dnt.itsnow.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <h1>MSP或者MSU的员工</h1>
 */
public class Staff extends ConfigItem {
    // 该员工的工号
    @NotBlank
    private String no;
    // 移动电话
    private String mobilePhone;
    // 固定电话
    private String fixedPhone;
    // 该员工的工作邮件,与user的mail未必一样
    @Email
    private String email;
    // 岗位，职位
    private String title;
    // 合同工，正式工
    private String type;
    // 在职，离职
    private String status;
    //所属部门
    private Department department;
    //所属地点
    private Site site;
    //该员工对应的用户，可能没有
    private User user;

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFixedPhone() {
        return fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Staff{");
        sb.append("no='").append(no).append('\'');
        sb.append(", name='").append(super.getName()).append('\'');
        sb.append(", mobilePhone='").append(mobilePhone).append('\'');
        sb.append(", fixedPhone='").append(fixedPhone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", department=").append(department);
        sb.append(", site=").append(site);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
