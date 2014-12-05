package dnt.itsnow.support;

import dnt.itsnow.model.PrivateServiceItem;
import dnt.itsnow.repository.PrivateServiceItemRepository;
import dnt.itsnow.service.PrivateServiceCatalogService;
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

    @Autowired
    private PrivateServiceCatalogService catalogService;


    @Override
    public List<PrivateServiceItem> findAllPrivate() {
        if(privateServiceItemList==null || privateServiceItemList.isEmpty())
            privateServiceItemList = privateServiceItemRepository.findAllPrivate();

        return privateServiceItemList;
    }

    @Override
    public PrivateServiceItem findPrivateBySn(String sn) {
        return privateServiceItemRepository.findPrivateBySn(sn);
    }

    @Override
    public PrivateServiceItem findPrivateByTitle(String title){
        List<PrivateServiceItem> items = findAllPrivate();
        for(PrivateServiceItem item:items){
            if(item.getTitle().equals(title))
                return item;
        }
        return null;
    }

    @Override
    public PrivateServiceItem savePrivate(PrivateServiceItem privateServiceItem) {
        setPrivateServiceItemList(null);
        catalogService.setPrivateServiceCatalogList(null);
        catalogService.setFormattedPrivateServiceCatalogList(null);
        privateServiceItemRepository.savePrivate(privateServiceItem);
        return privateServiceItem;
    }

    @Override
    public PrivateServiceItem updatePrivate(PrivateServiceItem privateServiceItem) {
        setPrivateServiceItemList(null);
        catalogService.setPrivateServiceCatalogList(null);
        catalogService.setFormattedPrivateServiceCatalogList(null);
        privateServiceItemRepository.updatePrivate(privateServiceItem);
        return privateServiceItem;
    }

    @Override
    public void deletePrivate(String sn) {
        setPrivateServiceItemList(null);
        catalogService.setPrivateServiceCatalogList(null);
        catalogService.setFormattedPrivateServiceCatalogList(null);
        privateServiceItemRepository.deletePrivate(sn);
    }

    public List<PrivateServiceItem> getPrivateServiceItemList() {
        return privateServiceItemList;
    }

    public void setPrivateServiceItemList(List<PrivateServiceItem> privateServiceItemList) {
        this.privateServiceItemList = privateServiceItemList;
    }
}
