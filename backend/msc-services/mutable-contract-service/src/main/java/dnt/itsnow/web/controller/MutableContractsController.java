/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Contracts Controller</h1>
 */
@RestController
@RequestMapping("/admin/api/contracts")
public class MutableContractsController extends SessionSupportController<Contract>{
    Contract contract;

    /**
     * <h2>获得所有的合同</h2>
     *
     * GET /admin/api/contracts
     *
     * @return 合同
     */
    @RequestMapping
    public List<Contract> index(){
        return null;
    }

    /**
     * <h2>查看一个合同</h2>
     *
     * GET /admin/api/contracts/{no}
     *
     * @return 合同
     */
    @RequestMapping("{no}")
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
        return contract;
    }

    /**
     * <h2>更新一个合同</h2>
     *
     * PUT /admin/api/contracts/{sn}
     *
     * @return 被更新的合同
     */
    @RequestMapping(value = "{no}", method = RequestMethod.PUT)
    public Contract update(@Valid @RequestBody Contract contract){
        this.contract.apply(contract);
        //TODO SAVE IT
        return this.contract;
    }

    /**
     * <h2>删除一个合同</h2>
     *
     * DELETE /admin/api/contracts/{sn}
     *
     * @return 被删除的合同
     */
    @RequestMapping(value = "{no}", method = RequestMethod.DELETE)
    public Contract destroy(){
        return null;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initContract(@PathVariable("no") String no){
        contract = null;//find it by sn
    }
}
