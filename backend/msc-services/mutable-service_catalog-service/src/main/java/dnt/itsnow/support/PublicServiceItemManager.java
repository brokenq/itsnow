package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.repository.PublicServiceItemRepository;
import dnt.itsnow.service.PublicServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by jacky on 2014/9/2.
 */
@Service
public class PublicServiceItemManager extends CommonServiceItemManager implements PublicServiceItemService {

    @Autowired
    PublicServiceItemRepository publicServiceItemRepository;

    @Override
    public PublicServiceItem create(PublicServiceItem publicServiceItem) {
        setCommonServiceItemList(null);
        publicServiceItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        publicServiceItem.setUpdatedAt(publicServiceItem.getCreatedAt());
        publicServiceItemRepository.save(publicServiceItem);
        return publicServiceItem;
    }

    @Override
    public PublicServiceItem update(PublicServiceItem publicServiceItem) {
        setCommonServiceItemList(null);
        publicServiceItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        publicServiceItemRepository.update(publicServiceItem);
        return publicServiceItem;
    }

    @Override
    public void delete(PublicServiceItem publicServiceItem) {
        setCommonServiceItemList(null);
        publicServiceItemRepository.delete(publicServiceItem.getSn());
    }

    @Override
    public void saveByAccount(Long itemId, Long accountId) {
        publicServiceItemRepository.addByAccount(itemId,accountId);
    }

    @Override
    public void deleteByAccount(Long itemId, Long accountId) {
        publicServiceItemRepository.deleteByAccount(itemId,accountId);
    }

}
