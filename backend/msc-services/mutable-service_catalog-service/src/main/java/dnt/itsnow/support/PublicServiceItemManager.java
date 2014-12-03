package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.repository.PublicServiceItemRepository;
import dnt.itsnow.service.PublicServiceCatalogService;
import dnt.itsnow.service.PublicServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * PublicServiceItemManager
 */
@Service
@Transactional
public class PublicServiceItemManager extends CommonServiceItemManager implements PublicServiceItemService {

    @Autowired
    PublicServiceItemRepository publicServiceItemRepository;

    @Autowired
    PublicServiceCatalogService catalogService;

    private List<PublicServiceItem> items;

    @Override
    public PublicServiceItem create(PublicServiceItem publicServiceItem) {
        setCommonServiceItemList(null);
        catalogService.setCommonServiceCatalogList(null);
        catalogService.setFormattedServiceCatalogList(null);
        publicServiceItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        publicServiceItem.setUpdatedAt(publicServiceItem.getCreatedAt());
        publicServiceItemRepository.save(publicServiceItem);
        return publicServiceItem;
    }

    @Override
    public PublicServiceItem update(PublicServiceItem publicServiceItem) {
        setCommonServiceItemList(null);
        catalogService.setCommonServiceCatalogList(null);
        catalogService.setFormattedServiceCatalogList(null);
        publicServiceItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        publicServiceItemRepository.update(publicServiceItem);
        return publicServiceItem;
    }

    @Override
    public void delete(PublicServiceItem publicServiceItem) {
        setCommonServiceItemList(null);
        catalogService.setCommonServiceCatalogList(null);
        catalogService.setFormattedServiceCatalogList(null);
        publicServiceItemRepository.delete(publicServiceItem.getSn());
    }

    @Override
    public void saveByAccount(PublicServiceItem item,Long accountId) {
        /*items = publicServiceItemRepository.findByAccountId(accountId);
        for(PublicServiceItem si:items){
            if(si.getId() == item.getId())
                return;
        }*/
        long count = publicServiceItemRepository.countByAccountAndItem(accountId,item.getId());
        if(count == 0)
            publicServiceItemRepository.addByAccount(item.getId(),accountId);
    }

    @Override
    public void deleteByAccount(PublicServiceItem item,Long accountId) {
        Set<Long> ids = new HashSet<Long>();
        ids.add(item.getId());
        publicServiceItemRepository.deleteByAccount(ids,accountId);
    }

}
