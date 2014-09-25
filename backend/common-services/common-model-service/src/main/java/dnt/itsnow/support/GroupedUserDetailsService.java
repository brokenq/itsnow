/**
 * xiongjie on 14-7-24.
 */
package dnt.itsnow.support;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import dnt.itsnow.repository.GroupRepository;
import dnt.itsnow.service.CommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <h1>根据UserRepository加载用户的UserDetailsService</h1>
 * 有两种方式实现这个 grouped 扩展
 * <ul>
 * <li>一种是继承</li>
 * <li>一种是委托</li>
 * </ul>
 * 当前采用了委托的方式
 *
 * TODO 添加测试用例
 */
@Service("groupedUserService")
public class GroupedUserDetailsService implements CommonUserService, UserDetailsService {
    @Autowired
    @Qualifier("plainUserService") // 代码中的qualifier 就是 context 中的bean名称或id
    CommonUserService userService;

    @Autowired
    GroupRepository groupRepository;

    @Override
    public User findByUsername(String username) {
        return userService.findByUsername(username);
    }

    @Override
    public boolean challenge(String username, String password) {
        return userService.challenge(username, password);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUserByUsername(username);
        user.addAuthorities(groupRepository.findUserAuthorities(username));
        return user;
    }

    @Override
    public Account findAccountById(Long accountId) {
        return userService.findAccountById(accountId);
    }
}
