/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

/**
 * <h1>MSP或者MSU的员工</h1>
 */
public class Staff extends ConfigItem {
    //该员工的工号
    private String no;
    //该员工在公司的别名，就像许多人，虽然有中文，但在公司大家都叫他Steven
    //private String nickName;
    //该员工的工作邮件,与user的mail未必一样
    private String email;
    //岗位，职位
    private String title;
    //员工状态
    private StaffStatus status;

    //所属部门
    private Department department;
    //该员工对应的用户，可能没有
    private User user;

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

    public StaffStatus getStatus() {
        return status;
    }

    public void setStatus(StaffStatus status) {
        this.status = status;
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
}
