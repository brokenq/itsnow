package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.repository.PublicServiceCatalogRepository;
import dnt.itsnow.repository.PublicServiceItemRepository;
import dnt.itsnow.service.PublicServiceItemService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jacky on 2014/9/2.
 */
@Service
public class PublicServiceItemManager extends Bean implements PublicServiceItemService {

    @Autowired
    PublicServiceCatalogRepository publicServiceCatalogRepository;

    @Autowired
    PublicServiceItemRepository publicServiceItemRepository;

    private static List<PublicServiceItem> publicServiceItemList;

    @Override
    public List<PublicServiceItem> findAll() {
        if(publicServiceItemList == null || publicServiceItemList.isEmpty()) {
            publicServiceItemList = publicServiceItemRepository.findAll();
        }
        return publicServiceItemList;
    }

    @Override
    public PublicServiceItem save(PublicServiceItem publicServiceItem) {
        return publicServiceItemRepository.save(publicServiceItem);
    }

    @Override
    public PublicServiceItem update(PublicServiceItem publicServiceItem) {
        return publicServiceItemRepository.update(publicServiceItem);
    }

    @Override
    public void delete(Long id) {
        publicServiceItemRepository.delete(id);
    }

    @Override
    public PublicServiceItem findById(Long id){
        if(publicServiceItemList == null || publicServiceItemList.isEmpty()) {
            publicServiceItemList = publicServiceItemRepository.findAll();
        }
        for(PublicServiceItem item:publicServiceItemList){
            if(item.getId()!=null && item.getId().equals(id))
                return item;
        }
        return null;
    }

}
