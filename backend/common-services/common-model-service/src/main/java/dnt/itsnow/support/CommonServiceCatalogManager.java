package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.ServiceCatalog;
import dnt.itsnow.repository.CommonServiceCatalogRepository;
import dnt.itsnow.service.CommonServiceCatalogService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky on 2014/9/3.
 */
@Service
public class CommonServiceCatalogManager extends Bean implements CommonServiceCatalogService {

    @Autowired
    CommonServiceCatalogRepository commonServiceCatalogRepository;

    private  List<PublicServiceCatalog> commonServiceCatalogList;

    @Override
    public List<PublicServiceCatalog> findAll() {
        if(commonServiceCatalogList==null || commonServiceCatalogList.isEmpty())
            commonServiceCatalogList = commonServiceCatalogRepository.findAll();

        List<PublicServiceCatalog> list = new ArrayList<PublicServiceCatalog>();
        for(PublicServiceCatalog node1 : commonServiceCatalogList){
            boolean mark = false;
            for(PublicServiceCatalog node2 : commonServiceCatalogList){
                if(node1.getParentId()!=null && node1.getParentId().equals(node2.getId())){
                    mark = true;
                    if(node2.getChildren() == null){
                        node2.setChildren(new ArrayList<ServiceCatalog>());
                    }
                    if(!node2.getChildren().contains(node1))
                        node2.getChildren().add(node1);
                    break;
                }
            }
            if(!mark){
                list.add(node1);
            }
        }
        return list;
    }

    @Override
    public PublicServiceCatalog findBySn(String sn) {
        return commonServiceCatalogRepository.findBySn(sn);
    }

    public List<PublicServiceCatalog> getCommonServiceCatalogList() {
        return commonServiceCatalogList;
    }

    public void setCommonServiceCatalogList(List<PublicServiceCatalog> commonServiceCatalogList) {
        this.commonServiceCatalogList = commonServiceCatalogList;
    }
}
