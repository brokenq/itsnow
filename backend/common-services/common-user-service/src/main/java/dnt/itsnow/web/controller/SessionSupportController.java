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

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>支持获取当前用户相关信息的基本控制器</h1>
 */
public class SessionSupportController<T extends Record> extends ApplicationController<T> {
    @Autowired
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
        String username = request.getRemoteUser();
        currentUser = userService.findByUsername(username);
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
