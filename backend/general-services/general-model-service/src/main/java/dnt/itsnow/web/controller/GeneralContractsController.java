/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.GeneralContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

/**
 * <h1>合同控制器</h1>
 * 面向当前用户所服务的企业，获取其相关的合同信息
 * <pre>
 * <b>HTTP     URI                        方法       含义  </b>
 * # PUT      /api/contracts/{sn}/approve approve   签订合同
 * # PUT      /api/contracts/{sn}/reject  reject    拒签合同
 * </pre>
 * 以上有损操作，并非是本系统直接执行（没有条件，没有权限）
 * 而是向msc发起请求执行(ContractService实现者)
 */
@RestController
@RequestMapping("/api/contracts")
public class GeneralContractsController extends SessionSupportController {
    @Autowired
    GeneralContractService contractService;

    /**
     * <h2>批准当前用户所服务的企业特定合同</h2>
     * <p/>
     * PUT /api/contracts/{sn}/approve
     *
     * @return 合同信息，不包括了Contract Detail 信息
     */
    @RequestMapping(value = "/{sn}/approve", method = RequestMethod.PUT)
    public Contract approve(@PathVariable("sn") String sn) {
        logger.info("Approving contract {}", sn);
        Contract contract;
        try {
            contract = contractService.approve(mainAccount, sn);
        } catch (ServiceException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE,
                    "the contract can't be approved:" + e.getMessage());
        } catch (RestClientException e) {
            throw new WebServerSideException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        logger.debug("Approved  contract {}", contract);
        return contract;
    }
    /**
     * <h2>拒绝当前用户所服务的企业特定合同</h2>
     * <p/>
     * PUT /api/contracts/{sn}/reject
     *
     * @return 合同信息，不包括了Contract Detail 信息
     */
    @RequestMapping(value = "/{sn}/reject", method = RequestMethod.PUT)
    public Contract reject( @PathVariable("sn") String sn) {
        logger.info("Rejecting contract {}", sn);
        Contract contract;
        try {
            contract = contractService.reject(mainAccount, sn);
        } catch (ServiceException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE,
                    "the contract can't be rejected:" + e.getMessage());
        } catch (RestClientException e) {
            throw new WebServerSideException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }
        logger.debug("Rejected  contract {}", contract);
        return contract;
    }

}
