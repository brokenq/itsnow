package dnt.itsnow.support;

import dnt.itsnow.model.PrivateServiceItem;
import dnt.itsnow.repository.PrivateServiceItemRepository;
import dnt.itsnow.service.PrivateServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>Private Service Item Manager</h1>
 */
@Service
public class PrivateServiceItemManager extends CommonServiceItemManager implements PrivateServiceItemService {
    @Autowired
    PrivateServiceItemRepository privateServiceItemRepository;

    private  List<PrivateServiceItem> privateServiceItemList;

    @Override
    public List<PrivateServiceItem> findAllPrivate() {
        if(privateServiceItemList==null || privateServiceItemList.isEmpty())
            privateServiceItemList = privateServiceItemRepository.findAllPrivate();

        return privateServiceItemList;
    }

    @Override
    public PrivateServiceItem findPrivateById(Long id) {
        return privateServiceItemRepository.findPrivateById(id);
    }

    @Override
    public PrivateServiceItem savePrivate(PrivateServiceItem privateServiceItem) {
        setPrivateServiceItemList(null);
        return privateServiceItemRepository.savePrivate(privateServiceItem);
    }

    @Override
    public void deletePrivate(Long id) {
        setPrivateServiceItemList(null);
        privateServiceItemRepository.deletePrivate(id);
    }

    public List<PrivateServiceItem> getPrivateServiceItemList() {
        return privateServiceItemList;
    }

    public void setPrivateServiceItemList(List<PrivateServiceItem> privateServiceItemList) {
        this.privateServiceItemList = privateServiceItemList;
    }
}
