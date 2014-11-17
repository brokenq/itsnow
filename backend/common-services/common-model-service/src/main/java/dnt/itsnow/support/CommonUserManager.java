/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.support;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import dnt.itsnow.repository.CommonUserRepository;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.CommonUserService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h1>Basic User Service</h1>
 *
 * TODO 添加测试用例
 */
@Service("plainUserService")
@Transactional
public class CommonUserManager extends Bean implements CommonUserService {

    @Autowired
    CommonUserRepository repository;
    @Autowired
    CommonAccountService accountService;

    PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

    @Override
    public User findByUsername(String username) {
        return repository.findByFieldAndValue("username", username);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByFieldAndValue("email", email);
    }

    @Override
    public User findByPhone(String phone) {
        return repository.findByFieldAndValue("phone", phone);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        // AppId 就是 Account的sn，忽略大小写
        String appId = System.getProperty("app.id");
        User user = repository.findByAccountSnAndUsername(appId, username);
        if (user == null) throw new UsernameNotFoundException("Can't find user " + username);
        user.setAuthorities(repository.findAuthorities(username));
        return user;
    }

    @Override
    public boolean challenge(String username, String password) {
        // AppId 就是 Account的sn，忽略大小写
        String appId = System.getProperty("app.id");
        User user = repository.findByAccountSnAndUsername(appId, username);
        if (user == null) throw new UsernameNotFoundException("Can't find user " + username);
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public Account findAccountById(Long accountId) {
        return accountService.findById(accountId);
    }

    /**
     * 对原始密码进行加密
     * @param rawPassword 原始密码
     * @return 被加密的密码
     */
    @SuppressWarnings("UnusedDeclaration")
    protected String encode(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public List<User> findUsersByAccount(Account mainAccount) {
        return repository.findUsersByAccount(mainAccount);
    }
}
