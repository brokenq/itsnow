/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.model.Account;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MutableAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * <h1>账户服务的控制器</h1>
 * <pre>
 * <b>HTTP     URI                            方法       含义  </b>
 * # GET      /admin/api/accounts            index     列出所有相关账户，支持过滤，分页，排序等
 * # GET      /admin/api/accounts/{sn}       show      列出特定的账户
 * # POST     /admin/api/accounts            create    创建账户，账户信息通过HTTP BODY提交
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
public class AccountsController extends ApplicationController {
    @Autowired
    MutableAccountService accountService;

    /**
     * <h2>查看所有账户</h2>
     * <p/>
     * GET /admin/api/accounts?type={msc|msu|msp|*}&page={int}&size={int}
     *
     * @param response HTTP 应答
     * @param type 账户类型，取值可以为 msc, msu, msp
     * @return 账户列表
     */
    @RequestMapping
    public List<Account> index( HttpServletResponse response, @RequestParam(value = "type", required = false) String type ) {
        logger.debug("Listing accounts");
        Page<Account> accounts = accountService.findAll(type, pageRequest);
        renderHeader(response, accounts);
        logger.debug("Found   accounts {}", accounts.getTotalElements());
        return accounts.getContent();
    }

    /**
     * <h2>查看特定账户</h2>
     * <p/>
     * GET /admin/api/accounts/{sn}
     */
    @RequestMapping("{sn}")
    public Account show(@PathVariable(value = "sn") String sn) {
        logger.debug("Viewing {}", sn);
        Account account = accountService.findBySn(sn);
        logger.debug("Viewed  {}", account);
        return account;
    }

    /**
     * <h2>创建一个账户</h2>
     * <p/>
     * POST /admin/api/accounts
     *
     * @param account 需要创建的账户对象，通过HTTP BODY POST上来
     * @return 创建之后的账户信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public Account create(@Valid Account account){
        logger.info("Creating {}", account.getName());
        // 可能会抛出重名的异常
        Account created = accountService.create(account);
        logger.info("Created  {} with sn {}", created.getName(), created.getSn());
        return created;
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
    public Account update(@PathVariable("sn") String sn,
                          @RequestBody @Valid Account account){
        logger.info("Updating {}", sn);
        Account exist = accountService.findBySn(sn);
        exist.apply(account);
        Account updated = null;
        try {
            updated = accountService.update(exist);
        } catch (AccountException e) {
            // 把service side异常转换为client side exception
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        logger.info("Updated  {}", updated);
        return updated;
    }

    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public void destroy(@PathVariable("sn") String sn){
        logger.info("Deleting {}", sn);
        try {
            accountService.delete(sn);
        } catch (AccountException e) {
            // 把service side异常转换为client side exception
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        logger.info("Deleted  {}", sn);
    }
}
