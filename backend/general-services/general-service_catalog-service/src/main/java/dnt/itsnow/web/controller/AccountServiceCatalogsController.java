/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PublicServiceCatalog;
import net.happyonroad.platform.web.annotation.BeforeFilter;
import net.happyonroad.platform.web.exception.WebClientSideException;
import net.happyonroad.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.AccountServiceCatalogService;
import dnt.itsnow.service.CommonServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

/**
 * <h1>私有服务项管理控制器</h1>
 */

@RestController
@RequestMapping("/api/public_service_catalogs/accounts/catalogs")
public class AccountServiceCatalogsController extends SessionSupportController<PublicServiceCatalog> {
    PublicServiceCatalog serviceCatalog;

    @Autowired
    AccountServiceCatalogService accountServiceCatalogService;

    @Autowired
    CommonServiceCatalogService commonServiceCatalogService;

    /**
     * <h2>增加帐户的一个服务目录</h2>
     *
     * PUT /api/public_service_catalogs/accounts/{sn}
     *
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.POST)
    public void createByAccount(@PathVariable("sn") String sn){
        try{
            accountServiceCatalogService.createByAccount(serviceCatalog, mainAccount);
        }catch (RestClientException e) {
            throw new WebServerSideException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }

    }

    /**
     * <h2>删除帐户的一个服务目录</h2>
     *
     * PUT /api/public_service_catalogs/accounts/{sn}
     *
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public void destroyByAccount(@PathVariable("sn") String sn){
        try {
            accountServiceCatalogService.deleteByAccount(serviceCatalog, mainAccount);
        }catch (RestClientException e) {
            throw new WebServerSideException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }
    }

    
    @BeforeFilter(order = 60, value = {"createByAccount", "destroyByAccount"})
    public void initServiceCatalog(@PathVariable("sn") String sn) {
        serviceCatalog = commonServiceCatalogService.findBySn(sn);
        if (serviceCatalog == null)
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "Can't find service catalog with sn:" + sn);
    }
}
