/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Contracts Controller</h1>
 */
@RestController
@RequestMapping("/admin/api/contracts/{sn}/details")
public class MutableContractDetailsController extends SessionSupportController<Contract> {

    private Contract currentContract;
    private ContractDetail currentDetail;

    /**
     * <h2>查看当特定合同下的所有明细</h2>
     * <p/>
     * GET /api/contracts/{sn}/details
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
        logger.info("Creating contract details {}: {}", detail.getTitle());
        return currentDetail;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ContractDetail update(@RequestBody @Valid ContractDetail detail){
        logger.info("Updating contract details {}: {}", detail.getTitle());
        return currentDetail;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ContractDetail destroy(@RequestBody @Valid ContractDetail detail){
        logger.info("Destroying contract details {}: {}", detail.getTitle());
        return currentDetail;
    }

    @BeforeFilter
    void initContract(@PathVariable("sn") String contractSn) throws ServiceException {
        currentContract = null;
    }
    @BeforeFilter(order = 60, value = {"update", "destroy"})
    void initDetail(@PathVariable("sn") String contractSn) throws ServiceException {
        currentDetail = null;
    }
}
