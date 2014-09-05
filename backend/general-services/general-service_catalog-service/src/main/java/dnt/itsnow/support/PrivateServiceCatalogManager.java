package dnt.itsnow.support;

import dnt.itsnow.model.PrivateServiceCatalog;
import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.ServiceCatalog;
import dnt.itsnow.repository.PrivateServiceCatalogRepository;
import dnt.itsnow.service.PrivateServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky on 2014/9/3.
 */
@Service
public class PrivateServiceCatalogManager extends CommonServiceCatalogManager implements PrivateServiceCatalogService {
    @Autowired
    PrivateServiceCatalogRepository privateServiceCatalogRepository;

    private  List<PrivateServiceCatalog> privateServiceCatalogList;

    @Override
    public List<PrivateServiceCatalog> findAllPrivate() {
        if(privateServiceCatalogList==null || privateServiceCatalogList.isEmpty())
            privateServiceCatalogList = privateServiceCatalogRepository.findAllPrivate();

        List<PrivateServiceCatalog> list = new ArrayList<PrivateServiceCatalog>();
        for(PrivateServiceCatalog node1 : privateServiceCatalogList){
            boolean mark = false;
            for(PublicServiceCatalog node2 : privateServiceCatalogList){
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
    public PrivateServiceCatalog findPrivateBySn(String sn) {
        return privateServiceCatalogRepository.findPrivateBySn(sn);
    }

    @Override
    public PrivateServiceCatalog savePrivate(PrivateServiceCatalog privateServiceCatalog) {
        setPrivateServiceCatalogList(null);
        return privateServiceCatalogRepository.savePrivate(privateServiceCatalog);
    }

    @Override
    public void deletePrivate(String sn) {
        setPrivateServiceCatalogList(null);
        privateServiceCatalogRepository.deletePrivate(sn);
    }

    public List<PrivateServiceCatalog> getPrivateServiceCatalogList() {
        return privateServiceCatalogList;
    }

    public void setPrivateServiceCatalogList(List<PrivateServiceCatalog> privateServiceCatalogList) {
        this.privateServiceCatalogList = privateServiceCatalogList;
    }
}
