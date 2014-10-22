/**
 * Developer: Kadvin Date: 14-8-27 下午4:39
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.model.Account;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MutableAccountService;
import dnt.itsnow.web.model.AccountRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * <h1>MSC/MSU注册</h1>
 * <pre>
 * <b>HTTP     URI                                         方法          含义  </b>
 * # POST      /public/accounts                            create       通过注册创建帐号
 * # POST      /public/accounts/check_unique/:field/:value checkUnique  检测字段唯一性
 * </pre>
 * 之所以单独搞一个控制器，是因为它与 MutableAccountController 中定义的其他方法有不同的使用者
 * 没法在同一个控制器/rest resource下进行分别授权
 */
@RestController
@RequestMapping("/public/accounts")
public class PublicAccountsController extends ApplicationController<Account> {
    @Autowired
    MutableAccountService accountService;
    Account currentAccount;

    /**
     * <h2>创建一个MSU账户</h2>
     * <p/>
     * POST /api/accounts
     *
     * @param registration 需要创建的帐户信息，通过HTTP BODY POST上来
     * @return 创建之后的账户信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public Account create(@RequestBody @Valid AccountRegistration registration, BindingResult result) {
        if( result.hasErrors() ){
            //TODO 抛出的异常 最终需要转换为前端可以处理的错误信息
            // status: 400
            // body:
            // {
            //   'registration.account.domain': '与系统保留的域名:www重复',
            //   'registration.user.username': '用户名:admin不唯一'
            // }
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, result.toString());
        }
        logger.info("Creating {} Account {}", registration.getType(), registration.getAccount());
        try{
            // 可能会抛出重名的异常(重名由数据库uk保证)
            currentAccount = accountService.register(registration.prepareAccount(), registration.getUser());
            logger.info("Created  {} account {} administrated by {}",
                        registration.getType(), currentAccount, registration.getUser());
            return currentAccount;
        }catch (AccountException ex){// user exception 也会被转换为account exception
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @RequestMapping("check/{field}/{value}")
    public HashMap checkUnique(@PathVariable("field") String field, @PathVariable("value") String value ){
        Account found;
        if("name".equalsIgnoreCase(field)){
            found = accountService.findByName(value);
        }else if("domain".equalsIgnoreCase(field)){
            found = accountService.findByDomain(value);
        }else{
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "Can't check uniqueness of account field: " + field);
        }
        if( found != null ){
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate field: " + field + " with value: " + value);
        }else{
            return new HashMap();
        }
    }
}
