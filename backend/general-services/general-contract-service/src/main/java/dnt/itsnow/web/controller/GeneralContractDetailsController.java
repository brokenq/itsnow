/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.GeneralContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>合同明细控制器</h1>
 * 面向当前用户所服务的企业，获取其相关的合同明细信息
 * <pre>
 * <b>HTTP     URI                        方法           含义  </b>
 * # GET      /api/contracts/{contract_sn}/details      index     列出所有相关合同明细
 * # PUT      /api/contracts/{contract_sn}/details/{sn} update    更新相关合同明细
 * </pre>
 * 以上有损操作，并非是当前子系统直接执行（没有权限）
 * 而是向msc发起请求执行
 */
@RestController
@RequestMapping("/api/contracts/{contract_sn}/details")
public class GeneralContractDetailsController extends SessionSupportController{
    @Autowired
    GeneralContractService contractService;

    private Contract currentContract;

    /**
     * <h2>查看当前用户所服务的企业的特定合同下的所有明细</h2>
     * <p/>
     * GET /api/contracts/{contract_sn}/details
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

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ContractDetail update(@PathVariable("id") Long id,
                                 @RequestBody @Valid ContractDetail detail){
        logger.info("Updating contract details {}: {}", id, detail.getTitle());
        // 因为这个contract被加载时已经load了所有的明细
        ContractDetail updated;
        try {
            updated = contractService.updateDetail(detail);
        } catch (ServiceException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE,
                    "the contract detail can't be updated:" + e.getMessage());
        } catch (RestClientException e) {
            throw new WebServerSideException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        logger.info("Updated  contract details {}", updated.getTitle());
        return updated;
    }

    @BeforeFilter(order = 30)
    void initContract(@PathVariable("contract_sn") String contractSn) throws ServiceException {
        currentContract = contractService.findByAccountAndSn(mainAccount, contractSn, true);
    }
}
