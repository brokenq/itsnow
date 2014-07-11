/**
 * Developer: Kadvin Date: 14-7-10 下午9:01
 */
package dnt.itsnow.backend.web.model;

/**
 * The Login Credential
 */
public class LoginCredential {

    private String  username;
    private String  password;
    private boolean remember;
    private String  captcha;
    private int maxAge = 30;// 天数

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
