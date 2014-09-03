/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.service.PublicServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>公用服务目录管理，</h1>
 */

@RestController
@RequestMapping("/admin/api/public_service_catalogs")
public class PublicServiceCatalogsController extends SessionSupportController<PublicServiceCatalog> {
    //PublicServiceCatalog serviceCatalog;
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
        return publicServiceCatalogService.findBySn(sn);
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
        return publicServiceCatalogService.save(serviceCatalog);
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
        //this.serviceCatalog.apply(serviceCatalog);
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
        publicServiceCatalogService.delete(sn);
        return null;
    }

    /*
    @BeforeFilter({"show", "update", "destroy"})
    public void initServiceCatalog(@PathVariable("sn") String sn){
        serviceCatalog = null;//find it by sn
    }*/

}
