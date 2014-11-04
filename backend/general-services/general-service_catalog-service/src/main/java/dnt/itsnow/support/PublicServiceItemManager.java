package dnt.itsnow.support;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.service.PublicServiceItemService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.Map;

/**
 * PublicServiceItemManager for remote invoke msc service
 */
@Service
public class PublicServiceItemManager extends Bean implements PublicServiceItemService {

    @Autowired
    RestOperations facade;

    @Override
    public void createByAccount(PublicServiceItem item, Account account) {

        ///admin/api/public_service_catalogs/{sn}/items/{isn}/remove?accountId=''
        //Map<String,String> var = new HashMap<String,String>();
        //var.put("accountId",account.getId()+"");
        //facade.put("/admin/api/public_service_catalogs/{sn}/items/{isn}/create",item.getCatalog().getSn(),item.getSn(),var);
        String url = "/admin/api/public_service_catalogs/"+item.getCatalog().getSn()+"/items/"+item.getSn()+"/account/"+account.getId()+"/create";
        //facade.put("/admin/api/public_service_catalogs/{sn}/items/{isn}/remove?accountId={id}",null,item.getCatalog().getSn(),item.getSn(),account.getId());
        facade.put(url,null);

        logger.info("Account:{} add public service item:{}", account.getName(),item.getSn());
    }

    @Override
    public void deleteByAccount(PublicServiceItem item, Account account) {
        Map<String,String> var = new HashMap<String,String>();
        var.put("accountId",account.getId()+"");
        String url = "/admin/api/public_service_catalogs/"+item.getCatalog().getSn()+"/items/"+item.getSn()+"/account/"+account.getId()+"/remove";
        //facade.put("/admin/api/public_service_catalogs/{sn}/items/{isn}/remove?accountId={id}",null,item.getCatalog().getSn(),item.getSn(),account.getId());
        facade.put(url,null);
        logger.info("Account:{} remove public service item:{}", account.getName(),item.getSn());
    }
}
