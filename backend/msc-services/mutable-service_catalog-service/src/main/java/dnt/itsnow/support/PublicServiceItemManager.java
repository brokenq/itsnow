package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.repository.PublicServiceItemRepository;
import dnt.itsnow.service.PublicServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jacky on 2014/9/2.
 */
@Service
public class PublicServiceItemManager extends CommonServiceItemManager implements PublicServiceItemService {

    @Autowired
    PublicServiceItemRepository publicServiceItemRepository;

    @Override
    public PublicServiceItem save(PublicServiceItem publicServiceItem) {
        setCommonServiceItemList(null);
        return publicServiceItemRepository.save(publicServiceItem);
    }

    @Override
    public PublicServiceItem update(PublicServiceItem publicServiceItem) {
        setCommonServiceItemList(null);
        return publicServiceItemRepository.update(publicServiceItem);
    }

    @Override
    public void delete(Long id) {
        setCommonServiceItemList(null);
        publicServiceItemRepository.delete(id);
    }

}
