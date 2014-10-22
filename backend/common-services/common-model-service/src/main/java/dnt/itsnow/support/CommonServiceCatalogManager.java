package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.ServiceItem;
import dnt.itsnow.repository.CommonServiceCatalogRepository;
import dnt.itsnow.service.CommonServiceCatalogService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Common ServiceCatalog Manager</h1>
 */
@Service
public class CommonServiceCatalogManager extends Bean implements CommonServiceCatalogService {

    @Autowired
    CommonServiceCatalogRepository commonServiceCatalogRepository;

    private  List<PublicServiceCatalog> commonServiceCatalogList;

    private  List<PublicServiceCatalog> formattedServiceCatalogList;

    @Override
    public List<PublicServiceCatalog> findAll() {
        getFormattedServiceCatalogList();


        //convert list to tree object
        List<PublicServiceCatalog> treeList = new ArrayList<PublicServiceCatalog>();

        for(PublicServiceCatalog catalog:formattedServiceCatalogList) {
            if(catalog.getParentId() == null) {
                this.formatTreeList(treeList,catalog,formattedServiceCatalogList);
            }
        }

        return treeList;

    }

    private void formatTreeList(List<PublicServiceCatalog> treeList,PublicServiceCatalog catalog,List<PublicServiceCatalog> children){
        treeList.add(catalog);
        for(PublicServiceCatalog node1 : children){
            if(node1.getParentId() != null && node1.getParentId().equals(catalog.getId())){
                this.formatTreeList(treeList,node1,children);
            }
        }
    }

    private List<PublicServiceCatalog> formatServiceCatalogs(List<PublicServiceCatalog> list){
        if(list == null || list.isEmpty())
            return list;
        for(PublicServiceCatalog catalog:list){
            formatServiceCatalog(catalog);
        }
        return list;
    }

    private void formatServiceCatalog(PublicServiceCatalog catalog){
        switch(catalog.getLevel()){
            case 1:
                for(ServiceItem item:catalog.getItems()){
                    item.setTitle("--"+item.getTitle());
                }
                break;
            case 2:
                //catalog.setParentId(1L);
                catalog.setTitle("--"+catalog.getTitle());
                for(ServiceItem item:catalog.getItems()){
                    item.setTitle("----"+item.getTitle());
                }
                break;
            case 3:
                //catalog.setParentId();
                catalog.setTitle("----"+catalog.getTitle());
                for(ServiceItem item:catalog.getItems()){
                    item.setTitle("------"+item.getTitle());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public PublicServiceCatalog findBySn(String sn) {
        return commonServiceCatalogRepository.findBySn(sn);
    }

    public List<PublicServiceCatalog> getCommonServiceCatalogList() {
        if(commonServiceCatalogList==null || commonServiceCatalogList.isEmpty()) {
            commonServiceCatalogList = commonServiceCatalogRepository.findAll();
        }
        return commonServiceCatalogList;
    }

    public void setFormattedServiceCatalogList(List<PublicServiceCatalog> formattedServiceCatalogList) {
        this.formattedServiceCatalogList = formattedServiceCatalogList;
    }

    public List<PublicServiceCatalog> getFormattedServiceCatalogList() {
        if(formattedServiceCatalogList==null || formattedServiceCatalogList.isEmpty()) {
            formattedServiceCatalogList = formatServiceCatalogs(getCommonServiceCatalogList());
        }
        return formattedServiceCatalogList;
    }

    public void setCommonServiceCatalogList(List<PublicServiceCatalog> commonServiceCatalogList) {
        this.commonServiceCatalogList = commonServiceCatalogList;
    }
}
