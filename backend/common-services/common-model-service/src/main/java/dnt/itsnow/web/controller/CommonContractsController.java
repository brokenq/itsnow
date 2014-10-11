/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.exception.ContractException;
import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.CommonContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1>合同控制器</h1>
 * 面向当前用户所服务的企业，获取其相关的合同信息
 * <pre>
 * <b>HTTP     URI                        方法       含义  </b>
 * # GET      /api/contracts              index     列出所有相关合同，支持过滤，分页，排序等
 * # GET      /api/contracts/{sn}         show      列出特定的合同
 * </pre>
 * TODO 添加测试用例
 */
@RestController
@RequestMapping("/api/contracts")
public class CommonContractsController extends SessionSupportController<Contract> {
    @Autowired
    CommonContractService contractService;

    /**
     * <h2>查看当前用户所服务的企业所有合同</h2>
     * <p/>
     * GET /api/contracts?page={int}&count={int}&order={string}
     *
     * @return 合同列表，其中的合同信息不包括Contract Detail
     */
    @RequestMapping
    public Page<Contract> index( ) {
        logger.debug("Listing contracts");
        try {
            indexPage = contractService.findAllByAccount(mainAccount, pageRequest);
        } catch (ServiceException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST,
                    "Your main account can't list contracts: " + e.getMessage());
        }
        logger.debug("Found   {}", indexPage);
        return indexPage;
    }

    /**
     * <h2>查看当前用户所服务的企业所有合同</h2>
     * <p/>
     * GET /api/contracts/{sn}
     *
     * @return 合同信息，包括了Contract Details
     */
    @RequestMapping("{sn}")
    public Contract show(@PathVariable("sn") String sn) {
        logger.debug("Viewing contract {}", sn);
        //如果找不到，应该抛出异常
        // 如果试图查看其主账户之外的合同，那会抛出额外的异常
        Contract contract;
        try {
            contract = contractService.findByAccountAndSn(mainAccount, sn, true);
        } catch (AccountException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST,
                    "Your main account can't view contract: " + e.getMessage());
        } catch (ContractException e) {
            throw new WebClientSideException(HttpStatus.UNAUTHORIZED,
                    "You are viewing a contract not belongs to your main account: " + e.getMessage());
        } catch (ServiceException e){
            throw new WebServerSideException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        logger.debug("Found   contract {}", contract);
        return contract;
    }

}
