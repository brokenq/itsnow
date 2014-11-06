/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MutableContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <h1>Contracts Controller</h1>
 * * <pre>
 * <b>HTTP     URI                                      方法       含义  </b>
 * # GET      /admin/api/contracts         index     列出所有合同，支持过滤，分页，排序等
 * # GET      /admin/api/contracts/{sn}    show      查看一个合同
 * # POST     /admin/api/contracts         create    创建合同
 * # PUT      /admin/api/contracts/{sn}/details/{id}    update    修改合同详情，账户信息通过HTTP BODY提交
 * # DELETE   /admin/api/contracts/{sn}/details/{id}    destroy   删除合同详情
 *
 */
@RestController
@RequestMapping("/admin/api/contracts")
public class MutableContractsController extends SessionSupportController<Contract> {
    Contract contract;
    @Autowired
    MutableContractService mutableContractService;

    /**
     * <h2>获得所有的合同</h2>
     *
     * GET /admin/api/contracts
     *
     * @return 合同
     */
    @RequestMapping(method=RequestMethod.GET)
    public Page<Contract> index(){
        logger.debug("Listing contracts");
        try {
            indexPage = mutableContractService.findAllByAccount(mainAccount,false, pageRequest);
            logger.debug("Found   {}", indexPage);
        }
        catch (ServiceException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return indexPage;
    }

    /**
     * <h2>查看一个合同</h2>
     *
     * GET /admin/api/contracts/{sn}
     *
     * @return 合同
     */
    @RequestMapping(value="{sn}",method=RequestMethod.GET)
    public Contract show(){
        return contract;
    }

    /**
     * <h2>创建一个合同</h2>
     *
     * POST /admin/api/contracts
     *
     * @return 新建的合同
     */
    @RequestMapping(method = RequestMethod.POST)
    public Contract create(@Valid @RequestBody Contract contract){
        return mutableContractService.create(contract);
    }

    /**
     * <h2>更新一个合同</h2>
     *
     * PUT /admin/api/contracts/{sn}
     *
     * @return 被更新的合同
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.PUT)
    public Contract update(@Valid @RequestBody Contract contract){
        this.contract.apply(contract);
        return mutableContractService.update(contract);
    }

    /**
     * <h2>MSP投标</h2>
     *
     * PUT /admin/api/contracts/{sn}/bid
     *
     * @return 合同
     */
    @RequestMapping(value = "{sn}/bid", method = RequestMethod.PUT)
    public Contract bid(@Valid @RequestBody Contract contract){
        logger.debug("msp bid contract:{}",contract.getSn());
        this.contract.apply(contract);
        return mutableContractService.bid(contract);
    }

    /**
     * <h2>MSU批准一个合同</h2>
     *
     * PUT /admin/api/contracts/{sn}/approve
     *
     * @return 被批准的合同
     */
    @RequestMapping(value = "{sn}/approve", method = RequestMethod.PUT)
    public Contract approve(){
        logger.debug("msu approve contract:{}", contract.getSn());
        return mutableContractService.approve(contract);
    }

    /**
     * <h2>MSU拒绝一个合同</h2>
     *
     * PUT /admin/api/contracts/{sn}/reject
     *
     * @return 被拒绝的合同
     */
    @RequestMapping(value = "{sn}/reject", method = RequestMethod.PUT)
    public Contract reject(){
        logger.debug("msu reject contract:{}",contract.getSn());
        return mutableContractService.reject(contract);
    }

    /**
     * <h2>删除一个合同</h2>
     *
     * DELETE /admin/api/contracts/{sn}
     *
     * @return 被删除的合同
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public Contract destroy(@PathVariable("sn")String sn){
        mutableContractService.delete(contract);
        return contract;
    }

    @BeforeFilter({"show", "update","bid","approve","reject", "destroy"})
    public void initContract(@PathVariable("sn")String sn){
        try {
            contract = mutableContractService.findByAccountAndSn(mainAccount, sn, true);//findByAccountId it by sn
        }catch(ServiceException e){
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE,e.getMessage());
        }
        if(contract == null){
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the contract with sn = " + sn);
        }
    }
}
