/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.PublicServiceCatalogService;
import dnt.itsnow.service.PublicServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>公用服务目录管理，</h1>
 */

@RestController
@RequestMapping("/admin/api/public_service_catalogs/{sn}/items")
public class PublicServiceItemsController extends SessionSupportController<PublicServiceItem> {
    PublicServiceCatalog serviceCatalog;
    PublicServiceItem serviceItem;

    @Autowired
    PublicServiceItemService publicServiceItemService;

    @Autowired
    PublicServiceCatalogService publicServiceCatalogService;

    /**
     * <h2>获得所有的服务项目</h2>
     *
     * GET /admin/api/public_service_catalogs/{sn}/items
     *
     * @return 服务项目
     */
    @RequestMapping
    public List<PublicServiceItem> index(){
        return publicServiceItemService.findAll();
    }
    
    /**
     * <h2>查看一个服务项目</h2>
     *
     * GET /admin/api/public_service_catalogs/{sn}/items/{id}
     *
     * @return 服务项目
     */
    @RequestMapping("{id}")
    public PublicServiceItem show(@PathVariable("id") Long id){
        return serviceItem;
    }

    /**
     * <h2>创建一个服务项目</h2>
     *
     * POST /admin/api/public_service_catalogs{sn}/items
     *
     * @return 新建的服务项目
     */
    @RequestMapping(method = RequestMethod.POST)
    public PublicServiceItem create(@Valid @RequestBody PublicServiceItem serviceItem){
        return publicServiceItemService.create(serviceItem);
    }

    /**
     * <h2>更新一个服务项目</h2>
     *
     * PUT /admin/api/public_service_catalogs/{sn}/items/{id}
     *
     * @return 被更新的服务项目
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public PublicServiceItem update(@Valid @RequestBody PublicServiceItem serviceItem){
        this.serviceItem.apply(serviceItem);
        return publicServiceItemService.update(serviceItem);
    }

    /**
     * <h2>删除一个服务项目</h2>
     *
     * DELETE /admin/api/public_service_catalogs/{sn}/items/{id}
     *
     * @return 被删除的服务项目
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public PublicServiceItem destroy(@PathVariable("id") Long id){
        publicServiceItemService.delete(serviceItem);
        return serviceItem;
    }

    /**
     * <h2>删除帐户的一个服务项目</h2>
     *
     * DELETE /admin/api/public_service_catalogs/{sn}/items/accounts/{id}
     *
     */
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
    public void destroyByAccount(@PathVariable("id") Long accountId,@RequestParam("itemId") Long itemId){
        publicServiceItemService.deleteByAccount(itemId,accountId);
    }

    /**
     * <h2>增加帐户的一个服务项目</h2>
     *
     * PUT /admin/api/public_service_catalogs/{sn}/items/accounts/{id}
     *
     */
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.PUT)
    public void addByAccount(@PathVariable("id") Long accountId,@RequestParam("itemId") Long itemId){
        publicServiceItemService.saveByAccount(itemId,accountId);
    }

    @BeforeFilter
    public void initServiceCatalog(@PathVariable("sn") String catalogSn){
        serviceCatalog = publicServiceCatalogService.findBySn(catalogSn);
        if(serviceCatalog == null)
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the service catalog with sn = " + catalogSn);

    }
    
    @BeforeFilter(order = 60, value = {"show", "update", "destroy"})
    public void initServiceItem(@PathVariable("id") Long id){
        if(serviceCatalog != null)
            serviceItem = (PublicServiceItem) serviceCatalog.getItemBySn(id);
        if(serviceItem == null)
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the service item with id: " + id);
    }
    
}
