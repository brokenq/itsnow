/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.*;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>公用服务目录管理，</h1>
 */

@RestController
@RequestMapping("/api/service_catalogs/{sn}/items")
public class PrivateServiceItemsController extends SessionSupportController<PrivateServiceItem> {
    ServiceCatalog serviceCatalog;
    PrivateServiceItem serviceItem;
    
    /**
     * <h2>获得所有的服务项目</h2>
     *
     * GET /api/service_catalogs/{sn}/items
     *
     * @return 服务项目
     */
    @RequestMapping
    public List<ServiceItem> index(){
        return null;
    }
    
    /**
     * <h2>查看一个服务项目</h2>
     *
     * GET /api/service_catalogs/{sn}/items/{id}
     *
     * @return 服务项目
     */
    @RequestMapping("{id}")
    public ServiceCatalog show(){
        return serviceCatalog;
    }

    /**
     * <h2>创建一个服务项目</h2>
     *
     * POST /api/service_catalogs{sn}/items
     *
     * @return 新建的服务项目
     */
    @RequestMapping(method = RequestMethod.POST)
    public PrivateServiceItem create(@Valid @RequestBody PrivateServiceItem serviceItem){
        return serviceItem;
    }

    /**
     * <h2>更新一个服务项目</h2>
     *
     * PUT /api/service_catalogs/{sn}/items/{id}
     *
     * @return 被更新的服务项目
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public PrivateServiceItem update(@Valid @RequestBody PrivateServiceItem serviceItem){
        this.serviceItem.apply(serviceItem);
        //TODO SAVE IT
        return this.serviceItem;
    }

    /**
     * <h2>删除一个服务项目</h2>
     *
     * DELETE /api/service_catalogs/{sn}/items/{id}
     *
     * @return 被删除的服务项目
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public PrivateServiceItem destroy(){
        return null;
    }

    @BeforeFilter
    public void initServiceCatalog(@PathVariable("sn") String catalogSn){
        serviceCatalog = null;//find it by sn, load with items
    }
    
    @BeforeFilter(order = 60, value = {"show", "update", "destroy"})
    public void initServiceItem(@PathVariable("id") Long id){
        serviceItem = (PrivateServiceItem) serviceCatalog.getItemBySn(id);
    }
    
}
