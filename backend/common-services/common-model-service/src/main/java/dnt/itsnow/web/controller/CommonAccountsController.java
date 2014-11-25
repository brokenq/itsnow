package dnt.itsnow.web.controller;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.service.CommonAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>账户控制器</h1>
 * 获取账户信息
 * <pre>
 * <b>HTTP     URI                                    方法          含义   </b>
 * # GET      /api/accounts/{sn}                     show          列出特定的账户
 * </pre>
 * TODO 添加测试用例
 */
@RestController
@RequestMapping("/api/accounts")
public class CommonAccountsController extends SessionSupportController<Contract> {

    @Autowired
    CommonAccountService accountService;

    @RequestMapping("{sn}")
    public Account show(@PathVariable("sn")String sn) {
        return accountService.findBySn(sn);
    }

}
