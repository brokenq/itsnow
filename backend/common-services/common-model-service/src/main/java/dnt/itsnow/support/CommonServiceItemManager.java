package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.repository.CommonServiceItemRepository;
import dnt.itsnow.service.CommonServiceItemService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jacky on 2014/9/3.
 */
@Service
public class CommonServiceItemManager extends Bean implements CommonServiceItemService {

    @Autowired
    CommonServiceItemRepository commonServiceItemRepository;

    private  List<PublicServiceItem> commonServiceItemList;

    @Override
    public List<PublicServiceItem> findAll() {
        if(commonServiceItemList==null || commonServiceItemList.isEmpty())
            commonServiceItemList = commonServiceItemRepository.findAll();

        return commonServiceItemList;
    }

    @Override
    public PublicServiceItem findById(Long id) {
        return commonServiceItemRepository.findById(id);
    }

    @Override
    public List<PublicServiceItem> findByAccountId(Long accountId) {
        return commonServiceItemRepository.findByAccountId(accountId);
    }

    public List<PublicServiceItem> getCommonServiceItemList() {
        return commonServiceItemList;
    }

    public void setCommonServiceItemList(List<PublicServiceItem> commonServiceItemList) {
        this.commonServiceItemList = commonServiceItemList;
    }
}
