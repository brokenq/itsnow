/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.web.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 修改密码的请求
 */
public class ChangePasswordRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    @Length(min = 6, max = 20)
    private String newPassword;
    @NotBlank
    @Repeat
    private String repeatPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
