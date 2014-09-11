/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

/**
 * <h1>MSP或者MSU的员工</h1>
 */
public class Staff extends ConfigItem {
    // 该员工的工号
    private String no;
    // 移动电话
    private String mobilePhone;
    // 固定电话
    private String fixedPhone;
    // 该员工的工作邮件,与user的mail未必一样
    private String email;
    // 岗位，职位
    private String title;
    // 合同工，正式工
    private String type;
    // 在职，离职
    private String status;
    //所属部门
    private Department department;
    //该员工对应的用户，可能没有
    private User user;

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

    public String getNickName() {
        return getName();
    }

    public void setNickName(String nickName) {
        setName(nickName);
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
        return "Staff{" +
                "no='" + no + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", fixedPhone='" + fixedPhone + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", department=" + department +
                ", user=" + user +
                '}';
    }
}
