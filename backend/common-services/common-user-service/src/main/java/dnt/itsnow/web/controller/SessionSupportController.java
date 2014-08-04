/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.User;
import dnt.itsnow.platform.model.Record;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * <h1>支持获取当前用户相关信息的基本控制器</h1>
 */
public class SessionSupportController<T extends Record> extends ApplicationController<T> {
    @Autowired
    @Qualifier("plainUserService")
    UserService    userService;

    // 用于before filter与实际方法之间的信息共享
    protected User    currentUser;
    protected Account mainAccount;

    // 初始化当前用户的主账户
    @BeforeFilter(order =  10)
    public void initCurrentUser(HttpServletRequest request) {
        //TODO 这段获取当前用户的主账户应该有全局服务
        //考虑的机制可能是扩展Spring Security的User Principal
        //将用户的staff信息，main account均存在principal里面
        //也就是封装一个包括 User/Account对象的Principal，就免得再次根据 username 查找用户了
        Principal principal = request.getUserPrincipal();
        if( principal instanceof Authentication){
            Authentication authentication = (Authentication)principal;
            currentUser = (User) authentication.getPrincipal();
        }else{
            String username = principal.getName();
            currentUser = userService.findByUsername(username);
        }
    }

    // 初始化当前用户的主账户
    @BeforeFilter(order =  20)
    public void initMainAccount() {
        //现在采用的是在用户上面映射了起当前主账户信息的方式
        // 所以找到主账户变得很简单
        this.mainAccount = currentUser.getAccount();
        // TODO mybatis association is not work now
        if(this.mainAccount == null){
            this.mainAccount = userService.findAccountById(currentUser.getAccountId());
        }
    }
}
