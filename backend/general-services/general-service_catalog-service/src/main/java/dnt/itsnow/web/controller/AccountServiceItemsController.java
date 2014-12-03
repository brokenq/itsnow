/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.AccountServiceItemService;
import dnt.itsnow.service.CommonServiceItemService;
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
@RequestMapping("/api/public_service_catalogs/accounts/items")
public class AccountServiceItemsController extends SessionSupportController<PublicServiceItem> {
    PublicServiceItem serviceItem;

    @Autowired
    AccountServiceItemService accountServiceItemService;

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
            accountServiceItemService.createByAccount(serviceItem, mainAccount);
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
            accountServiceItemService.deleteByAccount(serviceItem, mainAccount);
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
