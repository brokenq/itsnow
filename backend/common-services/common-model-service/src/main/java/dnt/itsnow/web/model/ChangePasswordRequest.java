/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.web.model;

import dnt.itsnow.model.PasswordContainer;
import dnt.itsnow.model.RepeatPassword;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 修改密码的请求
 */
@RepeatPassword
public class ChangePasswordRequest implements PasswordContainer{
    @NotBlank
    private String oldPassword;
    @NotBlank
    @Length(min = 6, max = 20)
    private String password;
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String username;
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
