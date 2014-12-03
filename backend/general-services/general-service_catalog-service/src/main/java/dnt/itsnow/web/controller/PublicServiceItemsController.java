/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PrivateServiceItem;
import dnt.itsnow.model.PublicServiceItem;
import net.happyonroad.platform.web.annotation.BeforeFilter;
import net.happyonroad.platform.web.exception.WebClientSideException;
import net.happyonroad.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.CommonServiceItemService;
import dnt.itsnow.service.PublicServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

/**
 * <h1>公共服务项管理控制器</h1>
 */

@RestController
@RequestMapping("/api/public_service_catalogs/accounts/items")
public class PublicServiceItemsController extends SessionSupportController<PrivateServiceItem> {
    PublicServiceItem serviceItem;

    @Autowired
    PublicServiceItemService publicServiceItemService;

    @Autowired
    CommonServiceItemService commonServiceItemService;

    /**
     * <h2>增加帐户的一个服务项目</h2>
     *
     * PUT /api/public_service_catalogs/accounts/items/{isn}
     *
     */
    @RequestMapping(value = "{isn}", method = RequestMethod.POST)
    public void createByAccount(@PathVariable("isn") String isn){
        try{
            publicServiceItemService.createByAccount(serviceItem, mainAccount);
        }catch (RestClientException e) {
            throw new WebServerSideException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }

    }

    /**
     * <h2>删除帐户的一个服务项目</h2>
     *
     * PUT /api/public_service_catalogs/accounts/items/{isn}
     *
     */
    @RequestMapping(value = "{isn}", method = RequestMethod.DELETE)
    public void destroyByAccount(@PathVariable("isn") String isn){
        try {
            publicServiceItemService.deleteByAccount(serviceItem, mainAccount);
        }catch (RestClientException e) {
            throw new WebServerSideException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }
    }

    
    @BeforeFilter(order = 60, value = {"createByAccount", "destroyByAccount"})
    public void initServiceItem(@PathVariable("isn") String isn) {
        serviceItem = commonServiceItemService.findBySn(isn);
        if (serviceItem == null)
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "Can't find service item with sn:" + isn);
    }
}
