package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.repository.CommonServiceItemRepository;
import dnt.itsnow.service.CommonServiceItemService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Common ServiceItem Manager</h1>
 */
@Service
public class CommonServiceItemManager extends Bean implements CommonServiceItemService {

    @Autowired
    CommonServiceItemRepository commonServiceItemRepository;

    private  List<PublicServiceItem> commonServiceItemList;

    @Override
    public List<PublicServiceItem> findAll() {
        return getCommonServiceItemList();
    }

    @Override
    public List<PublicServiceItem> findAllByCatalogSn(String sn) {
        List<PublicServiceItem> items = new ArrayList<PublicServiceItem>();
        List<PublicServiceItem> list = getCommonServiceItemList();
        for(PublicServiceItem item:list){
            PublicServiceCatalog catalog = item.getCatalog();
            if(catalog != null && sn.equals(catalog.getSn()))
                items.add(item);
        }
        return items;
    }

    @Override
    public PublicServiceItem findBySn(String sn) {
        return commonServiceItemRepository.findBySn(sn);
    }

    @Override
    public List<PublicServiceItem> findByAccountId(Long accountId) {
        return commonServiceItemRepository.findByAccountId(accountId);
    }

    public List<PublicServiceItem> getCommonServiceItemList() {
        if(commonServiceItemList==null || commonServiceItemList.isEmpty())
            commonServiceItemList = commonServiceItemRepository.findAll();
        return commonServiceItemList;
    }

    public void setCommonServiceItemList(List<PublicServiceItem> commonServiceItemList) {
        this.commonServiceItemList = commonServiceItemList;
    }
}
