package dnt.itsnow.web.controller;

import dnt.itsnow.model.PrivateServiceCatalog;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.service.PrivateServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>私有服务目录管理，</h1>
 */

@RestController
@RequestMapping("/api/private_service_catalogs")
public class PrivateServiceCatalogsController extends SessionSupportController<PrivateServiceCatalog> {
    PrivateServiceCatalog serviceCatalog;
    @Autowired
    PrivateServiceCatalogService privateServiceCatalogService;

    /**
     * <h2>获得所有的私有服务目录</h2>
     *
     * GET /api/private_service_catalogs
     *
     * @return 按照层次划分的服务目录
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<PrivateServiceCatalog> index(){
        List<PrivateServiceCatalog> list = privateServiceCatalogService.findAllPrivate();
        logger.debug("Get private_service_catalogs size:{}",list.size());
        return list;
    }

    /**
     * <h2>查看一个私有服务目录</h2>
     *
     * GET /api/private_service_catalogs/{sn}
     *
     * @return 服务目录
     */
    @RequestMapping(value = "/{sn}",method = RequestMethod.GET)
    public PrivateServiceCatalog show(@PathVariable("sn") String sn){
        return serviceCatalog;
    }

    /**
     * <h2>添加一个服务目录至私有服务目录</h2>
     *
     * POST /api/private_service_catalogs/{sn}
     *
     * @return 被更新的服务目录
     */
    @RequestMapping(method = RequestMethod.POST)
    public PrivateServiceCatalog add(@Valid @RequestBody PrivateServiceCatalog serviceCatalog){
        return privateServiceCatalogService.savePrivate(serviceCatalog);
    }

    /**
     * <h2>删除一个私有服务目录</h2>
     *
     * DELETE /api/private_service_catalogs/{sn}
     *
     * @return 被删除的服务目录
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public PrivateServiceCatalog destroy(@PathVariable("sn") String sn){
        privateServiceCatalogService.deletePrivate(sn);
        return null;
    }

    @BeforeFilter({"show"})
    public void initServiceCatalog(@PathVariable("sn") String catalogSn){
        serviceCatalog = privateServiceCatalogService.findPrivateBySn(catalogSn);
    }

}
