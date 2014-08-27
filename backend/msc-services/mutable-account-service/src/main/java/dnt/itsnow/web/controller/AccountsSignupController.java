/**
 * Developer: Kadvin Date: 14-8-27 下午4:39
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.MspAccount;
import dnt.itsnow.model.MsuAccount;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.service.MutableAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <h1>MSC/MSU注册</h1>
 * <pre>
 * <b>HTTP     URI                            方法       含义  </b>
 * # POST      /msu/signup                createMsu     通过注册创建MSU帐号
 * # POST      /msp/signup                createMsp     通过注册创建MSP帐号
 * </pre>
 */
@RestController
@RequestMapping("/api")
public class AccountsSignupController extends ApplicationController<Account> {
    @Autowired
    MutableAccountService accountService;
    Account currentAccount;

    /**
     * <h2>创建一个MSU账户</h2>
     * <p/>
     * POST /msu/signup
     *
     * @param account 需要创建的MSU帐户对象，通过HTTP BODY POST上来
     * @return 创建之后的账户信息
     */
    @RequestMapping(value = "/msu/signup", method = RequestMethod.POST)
    public MsuAccount createMsu(@RequestBody @Valid MsuAccount account){
        logger.info("Creating MSU {}", account.getName());
        // 可能会抛出重名的异常
        currentAccount = accountService.create(account);
        logger.info("Created  MSU {} with sn {}", currentAccount.getName(), currentAccount.getSn());
        return (MsuAccount) currentAccount;
    }

    /**
     * <h2>创建一个MSP账户</h2>
     * <p/>
     * POST /msp/signup
     *
     * @param account 需要创建的MSP帐户对象，通过HTTP BODY POST上来
     * @return 创建之后的账户信息
     */
    @RequestMapping(value = "/msp/signup",method = RequestMethod.POST)
    public MspAccount createMsp(@RequestBody @Valid MspAccount account){
        logger.info("Creating MSP {}", account.getName());
        // 可能会抛出重名的异常
        currentAccount = accountService.create(account);
        logger.info("Created  MSP {} with sn {}", currentAccount.getName(), currentAccount.getSn());
        return (MspAccount) currentAccount;
    }

}
