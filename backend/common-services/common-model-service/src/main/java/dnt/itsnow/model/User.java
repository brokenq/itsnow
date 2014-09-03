/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * <h1>系统的某个用户，一般与甲方或者乙方存在工作雇佣关系</h1>
 *
 * user也是配置项
 *
 * TODO 添加测试用例
 */
public class User extends ConfigItem implements UserDetails, CredentialsContainer {

    //~ Instance fields ================================================================================================
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    private String password;
    private Long accountId;
    @JsonIgnore
    // 用户的当前主账户，可以为空，可以变化
    private Account account;
    @JsonIgnore
    private Set<GrantedAuthority> authorities;
    private boolean enabled = true;   // 由一般注册流程或者管理员设置，该信息存储在user上
    private boolean expired = false;  //帐号过期
    private boolean locked =  false;  // 由多次尝试登录导致的临时锁定，该信息并不存储在user上
    private boolean passwordExpired = false;// 由密码策略设置的密码是否过期，该信息也不存储在user上

    //~ Constructors ===================================================================================================

    public User() {
    }

    //~ Methods ========================================================================================================

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @NotBlank
    @Length(min = 4, max = 20)
    public String getUsername() {
        return getName();
    }

    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !locked;
    }


    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !passwordExpired;
    }

    public void eraseCredentials() {
        password = null;
    }

    public void setUsername(String username) {
        setName(username);
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

    public void setPassword(String password) {
        this.password = password;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities);
        this.authorities = sortAuthorities(authorities);
    }

    public void addAuthorities(Set<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities);
        this.authorities.addAll(authorities);
        this.authorities = sortAuthorities(this.authorities);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    public void apply(User another) {
        this.setName(another.getName());
        this.email = another.email;
        this.phone = another.phone;
        this.enabled = another.enabled;
        this.expired = another.expired;
        this.locked = another.locked;
        this.passwordExpired = another.passwordExpired;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing the
     * same principal.
     */
    @Override
    public boolean equals(Object rhs) {
        //noinspection SimplifiableIfStatement
        if (rhs instanceof User) {
            return getName().equals(((User) rhs).getName());
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.getUsername()).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.isAccountNonExpired()).append("; ");
        sb.append("credentialsNonExpired: ").append(this.isCredentialsNonExpired()).append("; ");
        sb.append("AccountNonLocked: ").append(this.isAccountNonLocked()).append("; ");

        if (authorities != null && !authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;
                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }
        return sb.toString();
    }
}
