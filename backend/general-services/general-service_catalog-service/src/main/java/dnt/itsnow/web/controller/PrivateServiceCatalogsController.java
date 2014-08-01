/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PrivateServiceCatalog;
import dnt.itsnow.model.ServiceCatalog;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>私有服务目录管理，</h1>
 */

@RestController
@RequestMapping("/api/service_catalogs")
public class PrivateServiceCatalogsController extends SessionSupportController<ServiceCatalog> {
    PrivateServiceCatalog serviceCatalog;

    /**
     * <h2>获得所有的服务目录</h2>
     *
     * GET /api/service_catalogs
     *
     * @return 按照层次划分的服务目录
     */
    @RequestMapping
    public List<ServiceCatalog> index(){
        return null;
    }

    /**
     * <h2>查看一个服务目录</h2>
     *
     * GET /api/service_catalogs/{sn}
     *
     * @return 服务目录
     */
    @RequestMapping("{sn}")
    public ServiceCatalog show(){
        return serviceCatalog;
    }

    /**
     * <h2>创建一个服务目录</h2>
     *
     * POST /api/service_catalogs
     *
     * @return 新建的服务目录
     */
    @RequestMapping(method = RequestMethod.POST)
    public PrivateServiceCatalog create(@Valid @RequestBody PrivateServiceCatalog serviceCatalog){
        return serviceCatalog;
    }

    /**
     * <h2>更新一个服务目录</h2>
     *
     * PUT /api/service_catalogs/{sn}
     *
     * @return 被更新的服务目录
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.PUT)
    public PrivateServiceCatalog update(@Valid @RequestBody PrivateServiceCatalog serviceCatalog){
        this.serviceCatalog.apply(serviceCatalog);
        //TODO SAVE IT
        return this.serviceCatalog;
    }

    /**
     * <h2>删除一个服务目录</h2>
     *
     * DELETE /api/service_catalogs/{sn}
     *
     * @return 被删除的服务目录
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public PrivateServiceCatalog destroy(){
        return null;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initServiceCatalog(@PathVariable("sn") String sn){
        serviceCatalog = null;//find it by sn
    }

}
