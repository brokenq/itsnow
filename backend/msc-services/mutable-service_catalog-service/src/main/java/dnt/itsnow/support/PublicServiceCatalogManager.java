package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.ServiceCatalog;
import dnt.itsnow.repository.PublicServiceCatalogRepository;
import dnt.itsnow.repository.PublicServiceItemRepository;
import dnt.itsnow.service.PublicServiceCatalogService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky on 2014/9/2.
 */
@Service
public class PublicServiceCatalogManager extends Bean implements PublicServiceCatalogService {

    @Autowired
    PublicServiceCatalogRepository publicServiceCatalogRepository;

    @Autowired
    PublicServiceItemRepository publicServiceItemRepository;

    private static List<PublicServiceCatalog> publicServiceCatalogList;

    @Override
    public List<PublicServiceCatalog> findAll() {
        if(publicServiceCatalogList == null || publicServiceCatalogList.isEmpty()) {
            publicServiceCatalogList = publicServiceCatalogRepository.findAll();
        }

        List<PublicServiceCatalog> list = new ArrayList<PublicServiceCatalog>();
        for(PublicServiceCatalog node1 : publicServiceCatalogList){
            boolean mark = false;
            for(PublicServiceCatalog node2 : publicServiceCatalogList){
                if(node1.getParentId()!=null && node1.getParentId().equals(node2.getId())){
                    mark = true;
                    if(node2.getChildren() == null){
                        node2.setChildren(new ArrayList<ServiceCatalog>());
                    }
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
    public PublicServiceCatalog save(PublicServiceCatalog publicServiceCatalog) {
        return publicServiceCatalogRepository.save(publicServiceCatalog);
    }

    @Override
    public PublicServiceCatalog update(PublicServiceCatalog publicServiceCatalog) {
        return publicServiceCatalogRepository.update(publicServiceCatalog);
    }

    @Override
    public void delete(String sn) {
        publicServiceCatalogRepository.delete(sn);
    }

    @Override
    public PublicServiceCatalog findBySn(String sn){
        if(publicServiceCatalogList == null || publicServiceCatalogList.isEmpty()) {
            publicServiceCatalogList = publicServiceCatalogRepository.findAll();
        }
        for(PublicServiceCatalog sc:publicServiceCatalogList){
            if(sc.getSn()!=null && sc.getSn().equals(sn))
                return sc;
        }
        return null;
    }

}
