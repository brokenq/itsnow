/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PrivateServiceCatalog;
import dnt.itsnow.model.PrivateServiceItem;
import net.happyonroad.platform.web.annotation.BeforeFilter;
import net.happyonroad.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.PrivateServiceCatalogService;
import dnt.itsnow.service.PrivateServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @RequestMapping("{isn}")
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
     * <h2>更新一个私有服务项目</h2>
     *
     * PUT /api/private_service_catalogs/{sn}/items/{isn}
     *
     * @return 被删除的服务项目
     */
    @RequestMapping(value = "{isn}", method = RequestMethod.PUT)
    public PrivateServiceItem update(@Valid @RequestBody PrivateServiceItem item){
        serviceItem.apply(item);
        privateServiceItemService.updatePrivate(serviceItem);
        return serviceItem;
    }

    /**
     * <h2>删除一个私有服务项目</h2>
     *
     * DELETE /api/private_service_catalogs/{sn}/items/{isn}
     *
     * @return 被删除的服务项目
     */
    @RequestMapping(value = "{isn}", method = RequestMethod.DELETE)
    public PrivateServiceItem destroy(@PathVariable("isn") String isn){
        privateServiceItemService.deletePrivate(isn);
        return serviceItem;
    }

    /**
     * <h2>检查服务项title是否有效</h2>
     * <p/>
     * 主要检查服务项的名称是否唯一；
     * @param title 服务项标题
     */
    @RequestMapping("checkTitle")
    public void checkTitle(@RequestParam(value = "title") String title){
        PrivateServiceItem item = privateServiceItemService.findPrivateByTitle(title);
        if(item != null)
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate item title: " +title);
    }

    /**
     * <h2>检查服务项sn是否有效</h2>
     * <p/>
     * 主要检查服务项的sn是否唯一；
     * @param sn 服务项sn
     */
    @RequestMapping("checkSn")
    public void checkSn(@RequestParam(value = "sn") String sn){
        PrivateServiceItem item = privateServiceItemService.findPrivateBySn(sn);
        PrivateServiceCatalog catalog = privateServiceCatalogService.findPrivateBySn(sn);
        if(item != null || catalog != null)
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate item sn: " +sn);
    }


    @BeforeFilter
    public void initServiceCatalog(@PathVariable("sn") String catalogSn){
        serviceCatalog = privateServiceCatalogService.findPrivateBySn(catalogSn);
        if(serviceCatalog == null)
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "Can't find private service catalog with sn:"+catalogSn);
    }
    
    @BeforeFilter(order = 60, value = {"show", "destroy","update"})
    public void initServiceItem(@PathVariable("isn") String isn){
        if(serviceCatalog != null) {
            serviceItem = (PrivateServiceItem) serviceCatalog.getItemBySn(isn);
            if(serviceItem == null)
                throw new WebClientSideException(HttpStatus.BAD_REQUEST, "Can't find private service item with sn:"+isn);
        }
    }
    
}
