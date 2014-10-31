/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PrivateServiceCatalog;
import dnt.itsnow.model.PrivateServiceItem;
import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.service.PrivateServiceCatalogService;
import dnt.itsnow.service.PrivateServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>私有服务项管理控制器</h1>
 */

@RestController
@RequestMapping("/api/private_service_catalogs/{sn}/items")
public class PrivateServiceItemsController extends SessionSupportController<PrivateServiceItem> {
    PrivateServiceCatalog serviceCatalog;
    PrivateServiceItem serviceItem;

    @Autowired
    PrivateServiceItemService privateServiceItemService;

    @Autowired
    PrivateServiceCatalogService privateServiceCatalogService;

    /**
     * <h2>获得所有的私有服务项目</h2>
     *
     * GET /api/private_service_catalogs/{sn}/items
     *
     * @return 服务项目
     */
    @RequestMapping
    public List<PrivateServiceItem> index(){
        return privateServiceItemService.findAllPrivate();
    }
    
    /**
     * <h2>查看一个私有服务项目</h2>
     *
     * GET /api/private_service_catalogs/{sn}/items/{isn}
     *
     * @return 服务项目
     */
    @RequestMapping("/{isn}")
    public PrivateServiceItem show(@PathVariable("isn") String isn){
        return privateServiceItemService.findPrivateBySn(isn);
    }

    /**
     * <h2>创建一个私有服务项目</h2>
     *
     * POST /api/private_service_catalogs/{sn}/items
     *
     * @return 新建的服务项目
     */
    @RequestMapping(method = RequestMethod.POST)
    public PrivateServiceItem create(@Valid @RequestBody PrivateServiceItem serviceItem){
        return privateServiceItemService.savePrivate(serviceItem);
    }


    /**
     * <h2>删除一个私有服务项目</h2>
     *
     * DELETE /api/private_service_catalogs/{sn}/items/{isn}
     *
     * @return 被删除的服务项目
     */
    @RequestMapping(value = "/{isn}", method = RequestMethod.DELETE)
    public PublicServiceItem destroy(@PathVariable("isn") String isn){
        privateServiceItemService.deletePrivate(isn);
        return serviceItem;
    }


    @BeforeFilter
    public void initServiceCatalog(@PathVariable("sn") String catalogSn){
        serviceCatalog = privateServiceCatalogService.findPrivateBySn(catalogSn);
    }
    
    @BeforeFilter(order = 60, value = {"show", "destroy"})
    public void initServiceItem(@PathVariable("isn") String isn){
        if(serviceCatalog != null)
            serviceItem = (PrivateServiceItem) serviceCatalog.getItemBySn(isn);
    }
    
}
