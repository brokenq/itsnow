/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MutableContractDetailService;
import dnt.itsnow.service.MutableContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Contracts Controller</h1>
 * * <pre>
 * <b>HTTP     URI                                      方法       含义  </b>
 * # GET      /admin/api/contracts/{sn}/details         index     列出所有合同详情，支持过滤，分页，排序等
 * # POST     /admin/api/contracts/{sn}/details         create    创建合同详情
 * # PUT      /admin/api/contracts/{sn}/details/{id}    update    修改合同详情，账户信息通过HTTP BODY提交
 * # DELETE   /admin/api/contracts/{sn}/details/{id}    destroy   删除合同详情
 *
 */
@RestController
@RequestMapping("/admin/api/contracts/{sn}/details")
public class MutableContractDetailsController extends SessionSupportController<Contract> {

    private Contract currentContract;
    private ContractDetail currentDetail;

    @Autowired
    MutableContractService mutableContractService;

    @Autowired
    MutableContractDetailService mutableContractDetailService;

    /**
     * <h2>查看当特定合同下的所有明细</h2>
     * <p/>
     * GET /admin/api/contracts/{sn}/details
     *
     * @return 合同明细列表
     */
    @RequestMapping
    public List<ContractDetail> index() {
        logger.debug("Listing contract details {}", currentContract);
        List<ContractDetail> details = currentContract.getDetails();
        logger.debug("Listed  contract details {}: {}", currentContract, details.size());
        return details;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ContractDetail create(@RequestBody @Valid ContractDetail detail){
        logger.info("Creating {}", detail);
        mutableContractDetailService.create(detail);
        logger.info("Created  {}", detail);
        return detail;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ContractDetail update(@RequestBody @Valid ContractDetail detail){
        logger.info("Updating contract details {}: {}", detail.getTitle());
        this.currentDetail.apply(detail);
        mutableContractDetailService.update(currentDetail);
        return currentDetail;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ContractDetail destroy(@RequestBody @Valid ContractDetail detail){
        logger.info("Destroying contract details {}: {}", detail.getTitle());
        currentContract.apply(detail);
        mutableContractDetailService.delete(currentDetail);
        return currentDetail;
    }

    @BeforeFilter
    void initContract(@PathVariable("sn") String contractSn) throws ServiceException {
        currentContract = mutableContractService.findByAccountAndSn(mainAccount,contractSn,true);
        if(currentContract == null){
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the contract with sn = " + contractSn);
        }
    }
    @BeforeFilter(order = 60, value = {"update", "destroy"})
    void initDetail(@PathVariable("sn") String contractSn,@PathVariable("id") Long detailId) throws ServiceException {
        currentDetail = currentContract.getDetail(detailId);
        if(currentDetail == null){
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the contract details with sn = " + contractSn+ "detail id:"+detailId);
        }
    }
}
