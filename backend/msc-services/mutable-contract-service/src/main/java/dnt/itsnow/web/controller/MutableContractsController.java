/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MutableContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Contracts Controller</h1>
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
    public List<Contract> index(){
        logger.debug("Listing contracts");
        try {
            indexPage = mutableContractService.findAllByAccount(mainAccount, pageRequest);
            logger.debug("Found   contracts {}", indexPage.getTotalElements());
        }
        catch (ServiceException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return indexPage.getContent();
    }

    /**
     * <h2>查看一个合同</h2>
     *
     * GET /admin/api/contracts/{sn}
     *
     * @return 合同
     */
    @RequestMapping(value="/{sn}",method=RequestMethod.GET)
    public Contract show(@PathVariable("sn")String sn){
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
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public Contract update(@Valid @RequestBody Contract contract){
        this.contract.apply(contract);
        return mutableContractService.update(contract);
    }

    /**
     * <h2>删除一个合同</h2>
     *
     * DELETE /admin/api/contracts/{sn}
     *
     * @return 被删除的合同
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public Contract destroy(@PathVariable("sn")String sn){
        mutableContractService.delete(contract);
        return null;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initContract(@PathVariable("sn") String sn) {
        try {
            contract = mutableContractService.findByAccountAndSn(mainAccount, sn, true);//findByAccountId it by sn
        }catch(ServiceException e){
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }
}
