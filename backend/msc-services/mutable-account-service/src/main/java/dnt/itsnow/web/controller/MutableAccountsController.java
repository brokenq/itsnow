/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.model.Account;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MutableAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>账户服务的控制器</h1>
 * <pre>
 * <b>HTTP     URI                            方法       含义  </b>
 * # GET      /admin/api/accounts            index     列出所有相关账户，支持过滤，分页，排序等
 * # GET      /admin/api/accounts/{sn}       show      列出特定的账户
 * # PUT      /admin/api/accounts/{sn}       update    修改账户，账户信息通过HTTP BODY提交
 * # DELETE   /admin/api/accounts/{sn}       destroy   删除账户
 *
 * 由于我们的系统架构是由前端angular项目直接展示页面，不是server side script生成页面，所以，传统 Rails Resources路由中的
 *
 * # GET /admin/accounts/new         (新增输入页面)
 * # GET /admin/accounts/{sn}/edit   (编辑修改页面)
 * # GET /admin/accounts/{sn}/delete (删除确认页面)
 *
 * 等无需提供SPI接口
 * </pre>
 */
@RestController
@RequestMapping("/admin/api/accounts")
public class MutableAccountsController extends SessionSupportController<Account> {
    @Autowired
    MutableAccountService accountService;
    Account currentAccount;

    /**
     * <h2>查看所有账户</h2>
     * <p/>
     * GET /admin/api/accounts?type={msc|msu|msp|*}&page={int}&size={int}
     *
     * @param type 账户类型，取值可以为 msc, msu, msp
     * @return 账户列表
     */
    @RequestMapping
    public List<Account> index( @RequestParam(value = "type", required = false) String type ) {
        logger.debug("Listing accounts");
        indexPage = accountService.findAll(type, pageRequest);
        logger.debug("Found   accounts {}", indexPage.getTotalElements());
        return indexPage.getContent();
    }

    /**
     * <h2>查看特定账户</h2>
     * <p/>
     * GET /admin/api/accounts/{sn}
     */
    @RequestMapping("{sn}")
    public Account show() {
        logger.debug("Viewed  {}", currentAccount);
        return currentAccount;
    }


    @RequestMapping("{sn}/approve")
    public Account approve(){
        logger.info("Approving {}", currentAccount.getSn());
        try {
            Account account = accountService.approve(currentAccount);
            logger.info("Approved  {}", account);
            return account;
        } catch (AccountException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @RequestMapping("{sn}/reject")
    public Account reject(){
        logger.info("Rejecting {}", currentAccount.getSn());
        try {
            Account account = accountService.reject(currentAccount);
            logger.info("Rejected  {}", account);
            return account;
        } catch (AccountException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    /**
     * <h2>更新一个账户</h2>
     * <p/>
     * PUT /admin/api/accounts/{sn}
     *
     * @param account 需要更新的账户对象，通过HTTP BODY POST上来
     * @return 更新之后的账户信息
     */
    @RequestMapping( value = "{sn}", method = RequestMethod.PUT)
    public Account update(@RequestBody @Valid Account account){
        logger.info("Updating {}", currentAccount.getSn());
        currentAccount.apply(account);
        Account updated;
        try {
            updated = accountService.update(currentAccount);
        } catch (AccountException e) {
            // 把service side异常转换为client side exception
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        logger.info("Updated  {}", updated);
        return updated;
    }

    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public void destroy(){
        logger.warn("Deleting {}", currentAccount.getSn());
        try {
            accountService.delete(currentAccount);
        } catch (AccountException e) {
            // 把service side异常转换为client side exception
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        logger.warn("Deleted  {}", currentAccount);
    }

    @BeforeFilter({"show", "update", "destroy", "approve", "reject"})
    public void initCurrentAccount(@PathVariable("sn") String sn){
        logger.debug("Finding account {}", sn);
        currentAccount = accountService.findBySn(sn);
        if( currentAccount == null )
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the account with sn = " + sn);
    }

}
