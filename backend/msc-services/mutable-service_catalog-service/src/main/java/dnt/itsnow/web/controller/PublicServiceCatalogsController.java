/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.PublicServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>公用服务目录管理，</h1>
 */

@RestController
@RequestMapping("/admin/api/public_service_catalogs")
public class PublicServiceCatalogsController extends SessionSupportController<PublicServiceCatalog> {
    PublicServiceCatalog serviceCatalog;
    @Autowired
    PublicServiceCatalogService publicServiceCatalogService;
    /**
     * <h2>获得所有的服务目录</h2>
     *
     * GET /admin/api/public_service_catalogs
     *
     * @return 按照层次划分的服务目录
     */
    @RequestMapping
    public List<PublicServiceCatalog> index(){
        List<PublicServiceCatalog> list = publicServiceCatalogService.findAll();
        logger.debug("Get public_service_catalogs size:{}",list.size());
        return list;
    }

    /**
     * <h2>查看一个服务目录</h2>
     *
     * GET /admin/api/public_service_catalogs/{sn}
     *
     * @return 服务目录
     */
    @RequestMapping("{sn}")
    public PublicServiceCatalog show(@PathVariable("sn") String sn){
        return serviceCatalog;
    }

    /**
     * <h2>创建一个服务目录</h2>
     *
     * POST /admin/api/public_service_catalogs
     *
     * @return 新建的服务目录
     */
    @RequestMapping(method = RequestMethod.POST)
    public PublicServiceCatalog create(@Valid @RequestBody PublicServiceCatalog serviceCatalog){
        return publicServiceCatalogService.create(serviceCatalog);
    }

    /**
     * <h2>更新一个服务目录</h2>
     *
     * PUT /admin/api/public_service_catalogs/{sn}
     *
     * @return 被更新的服务目录
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.PUT)
    public PublicServiceCatalog update(@Valid @RequestBody PublicServiceCatalog serviceCatalog){
        this.serviceCatalog.apply(serviceCatalog);
        return publicServiceCatalogService.update(serviceCatalog);
    }

    /**
     * <h2>删除一个服务目录</h2>
     *
     * DELETE /admin/api/public_service_catalogs/{sn}
     *
     * @return 被删除的服务目录
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public PublicServiceCatalog destroy(@PathVariable("sn") String sn){
        publicServiceCatalogService.delete(serviceCatalog);
        return serviceCatalog;
    }

    /**
     * <h2>删除帐户的一个服务目录</h2>
     *
     * PUT /admin/api/public_service_catalogs/{sn}/account/{account}/remove
     *
     */
    @RequestMapping(value = "{sn}/account/{accountId}/remove", method = RequestMethod.PUT)
    public void destroyByAccount(@PathVariable("sn") String sn,@PathVariable("accountId") Long accountId){
        logger.info("Remove service catalog {} of Account:{}",sn,accountId);
        publicServiceCatalogService.deleteByAccount(serviceCatalog,accountId);
        logger.info("Removed service catalog {} of Account:{}",sn,accountId);
    }

    /**
     * <h2>增加帐户的一个服务目录</h2>
     *
     * PUT /admin/api/public_service_catalogs/{sn}/account/{accountId}/create
     *
     */
    @RequestMapping(value = "{sn}/account/{accountId}/create", method = RequestMethod.PUT)
    public void addByAccount(@PathVariable("sn") String sn,@PathVariable("accountId") Long accountId){
        logger.info("Add service catalog {} of Account:{}",sn,accountId);
        publicServiceCatalogService.saveByAccount(serviceCatalog,accountId);
        logger.info("Added service catalog {} of Account:{}",sn,accountId);
    }


    @BeforeFilter({"show", "update", "destroy","addByAccount","destroyByAccount"})
    public void initServiceCatalog(@PathVariable("sn") String sn){
        serviceCatalog = publicServiceCatalogService.findBySn(sn);
        if(serviceCatalog == null)
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the service catalog with sn = " + sn);
    }

}
