/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.PublicServiceItem;
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
@RequestMapping("/api/public_service_catalogs")
public class CommonServiceCatalogsController extends SessionSupportController<PublicServiceCatalog> {
    PublicServiceCatalog serviceCatalog;
    @Autowired
    CommonServiceCatalogService commonServiceCatalogService;

    @Autowired
    CommonServiceItemService commonServiceItemService;
    /**
     * <h2>获得所有的服务目录</h2>
     *
     * GET /admin/api/public_service_catalogs
     *
     * @return 按照层次划分的服务目录
     */
    @RequestMapping
    public List<PublicServiceCatalog> index(){

        List<PublicServiceCatalog> list = commonServiceCatalogService.findAll();
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
        return commonServiceCatalogService.findBySn(sn);
    }

    /**
     * <h2>获得该帐户下所有的服务项</h2>
     *
     * GET /api/public_service_catalogs/accounts/{accountId}
     *
     * @return 服务项列表
     */
    @RequestMapping("/accounts/items")
    public List<PublicServiceItem> indexAccount(){
        return commonServiceItemService.findByAccountId(mainAccount.getId());
    }


}
