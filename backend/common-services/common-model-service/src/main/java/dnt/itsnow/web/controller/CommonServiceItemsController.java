/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.service.CommonServiceCatalogService;
import dnt.itsnow.service.CommonServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>公用服务目录管理，</h1>
 */

@RestController
@RequestMapping("/api/public_service_catalogs/{sn}/items")
public class CommonServiceItemsController extends SessionSupportController<PublicServiceItem> {
    PublicServiceCatalog serviceCatalog;
    PublicServiceItem serviceItem;

    @Autowired
    CommonServiceItemService commonServiceItemService;

    @Autowired
    CommonServiceCatalogService commonServiceCatalogService;

    /**
     * <h2>获得所有的服务项目</h2>
     *
     * GET /api/public_service_catalogs/{sn}/items
     *
     * @return 服务项目
     */
    @RequestMapping
    public List<PublicServiceItem> index(){
        return commonServiceItemService.findAll();
    }
    
    /**
     * <h2>查看一个服务项目</h2>
     *
     * GET /api/public_service_catalogs/{sn}/items/{id}
     *
     * @return 服务项目
     */
    @RequestMapping("{id}")
    public PublicServiceItem show(@PathVariable("id") Long id){
        return commonServiceItemService.findById(id);
    }


    @BeforeFilter
    public void initServiceCatalog(@PathVariable("sn") String catalogSn){
        serviceCatalog = commonServiceCatalogService.findBySn(catalogSn);
    }
    
    @BeforeFilter(order = 60, value = {"show", "update", "destroy"})
    public void initServiceItem(@PathVariable("id") Long id){
        if(serviceCatalog != null)
            serviceItem = (PublicServiceItem) serviceCatalog.getItemBySn(id);
    }
    
}
