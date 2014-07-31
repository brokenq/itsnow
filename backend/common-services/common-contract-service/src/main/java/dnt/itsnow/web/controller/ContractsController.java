/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <h1>合同控制器</h1>
 * 面向当前用户所服务的企业，获取其相关的合同信息
 * <pre>
 * <b>HTTP     URI                        方法       含义  </b>
 * # GET      /api/contracts              index     列出所有相关合同，支持过滤，分页，排序等
 * # GET      /api/contracts/{sn}         show      列出特定的合同
 * # PUT      /api/contracts/{sn}/approve approve   签订合同
 * # PUT      /api/contracts/{sn}/reject  reject    拒签合同
 * </pre>
 * 以上有损操作，并非是本系统直接执行（没有条件，没有权限）
 * 而是向msc发起请求执行(ContractService实现者)
 *
 * <p>
 * TODO 以下代码，显然发现了共性: 控制器层面的 before/after filter
 * 不过，如果全局层面通过Servlet Filter实现了当前用户主账户的查找
 * 这里的控制器的before/after filter的作用就弱化为一种代码精简(Code Reduce)
 * </p>
 */
@RestController
@RequestMapping("/api/contracts")
public class ContractsController extends SessionSupportController {
    @Autowired
    ContractService contractService;

    /**
     * <h2>查看当前用户所服务的企业所有合同</h2>
     * <p/>
     * GET /api/contracts?page={int}&size={int}
     *
     * @param response HTTP 应答
     * @return 合同列表，其中的合同信息不包括Contract Detail
     */
    @RequestMapping
    public List<Contract> index( HttpServletResponse response ) {
        logger.debug("Listing contracts");
        Page<Contract> contracts = contractService.findAllByAccount(mainAccount, pageRequest);
        renderHeader(response, contracts);
        logger.debug("Found   contracts {}", contracts.getTotalElements());
        return contracts.getContent();
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
        Contract contract = contractService.findByAccountAndSn(mainAccount, sn, true);
        logger.debug("Found   contract {}", contract);
        return contract;
    }

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
        Contract contract = contractService.approve(mainAccount, sn);
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
        logger.info("Approving contract {}", sn);
        Contract contract = contractService.reject(mainAccount, sn);
        logger.debug("Approved  contract {}", contract);
        return contract;
    }


}
