package dnt.itsnow.web.controller;

import dnt.itsnow.model.PrivateServiceCatalog;
import dnt.itsnow.model.PrivateServiceItem;
import dnt.itsnow.service.PrivateServiceCatalogService;
import dnt.itsnow.service.PrivateServiceItemService;
import net.happyonroad.platform.web.annotation.BeforeFilter;
import net.happyonroad.platform.web.exception.WebClientSideException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>私有服务目录管理控制器</h1>
 */

@RestController
@RequestMapping("/api/private_service_catalogs")
public class PrivateServiceCatalogsController extends SessionSupportController<PrivateServiceCatalog> {
    PrivateServiceCatalog serviceCatalog;
    @Autowired
    PrivateServiceCatalogService privateServiceCatalogService;

    @Autowired
    PrivateServiceItemService privateServiceItemService;

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
    @RequestMapping(value = "{sn}",method = RequestMethod.GET)
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
    public PrivateServiceCatalog create(@Valid @RequestBody PrivateServiceCatalog serviceCatalog){
        return privateServiceCatalogService.savePrivate(serviceCatalog);
    }

    /**
     * <h2>更新一个服务目录</h2>
     *
     * PUT /api/private_service_catalogs/{sn}
     *
     * @return 被更新的服务目录
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.PUT)
    public PrivateServiceCatalog update(@Valid @RequestBody PrivateServiceCatalog serviceCatalog){
        this.serviceCatalog.apply(serviceCatalog);
        return privateServiceCatalogService.updatePrivate(serviceCatalog);
    }

    /**
     * <h2>删除一个私有服务目录</h2>
     *
     * DELETE /api/private_service_catalogs/{sn}
     *
     * @return 被删除的服务目录
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public PrivateServiceCatalog destroy(@PathVariable("sn") String sn){
        privateServiceCatalogService.deletePrivate(serviceCatalog);
        return serviceCatalog;
    }

    /**
     * <h2>检查服务目录title是否有效</h2>
     * <p/>
     * 主要检查服务目录的名称是否唯一；
     * @param title 服务目录标题
     */
    @RequestMapping("checkTitle")
    public void checkTitle(@RequestParam(value = "title") String title){
        PrivateServiceCatalog catalog = privateServiceCatalogService.findPrivateByTitle(title);
        if(catalog != null)
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate catalog title: " +title);
    }

    /**
     * <h2>检查服务目录sn是否有效</h2>
     * <p/>
     * 主要检查服务目录的sn是否唯一；
     * @param sn 服务目录sn
     */
    @RequestMapping("checkSn")
    public void checkSn(@RequestParam(value = "sn") String sn){
        PrivateServiceItem item = privateServiceItemService.findPrivateBySn(sn);
        PrivateServiceCatalog catalog = privateServiceCatalogService.findPrivateBySn(sn);
        if(item != null || catalog != null)
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate catalog sn: " +sn);
    }

    @BeforeFilter({"show","destroy","update"})
    public void initServiceCatalog(@PathVariable("sn") String catalogSn){
        serviceCatalog = privateServiceCatalogService.findPrivateBySn(catalogSn);
        if(serviceCatalog == null)
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "Can't find private service catalog with sn:"+catalogSn);
    }

}
