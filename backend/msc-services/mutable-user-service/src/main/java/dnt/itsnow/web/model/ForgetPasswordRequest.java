/**
 * @author XiongJie, Date: 14-7-29
 */
package dnt.itsnow.web.model;

/**
 * <h1>忘记密码的请求</h1>
 * 有多种实现该请求的机制，例如：
 *
 * <li>系统重置，而后发送信息（邮件或者短信）到用户提供的地址
 * <li>也可以先发送信息到用户所提供的地址，引导用户重置
 */
public class ForgetPasswordRequest {
    private String email;
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
