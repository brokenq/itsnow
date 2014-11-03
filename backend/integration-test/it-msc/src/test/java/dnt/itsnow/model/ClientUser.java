/**
 * xiongjie on 14-8-11.
 */
package dnt.itsnow.model;

/**
 * <h1>对应于User的Client Json解析对象</h1>
 */
public class ClientUser extends ClientConfigItem{
    private String email;
    private String phone;
    private String password;
    private String repeatPassword;
    private String accountId;
    private boolean enabled;
    private boolean expired;
    private boolean locked;
    private boolean passwordExpired;
    private ClientAccount account;

    public ClientAccount getAccount() {
        return account;
    }

    public void setAccount(ClientAccount account) {
        this.account = account;
    }

    public String getUsername() {
        return getName();
    }

    public void setUsername(String username) {
        this.setName(username);
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
