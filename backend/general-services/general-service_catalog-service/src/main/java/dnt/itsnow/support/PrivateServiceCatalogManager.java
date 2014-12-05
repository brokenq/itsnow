package dnt.itsnow.support;

import dnt.itsnow.model.PrivateServiceCatalog;
import dnt.itsnow.model.ServiceItem;
import dnt.itsnow.repository.PrivateServiceCatalogRepository;
import dnt.itsnow.service.PrivateServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Private Service Catalog Manager</h1>
 */
@Service
public class PrivateServiceCatalogManager extends CommonServiceCatalogManager implements PrivateServiceCatalogService {
    @Autowired
    PrivateServiceCatalogRepository privateServiceCatalogRepository;

    private  List<PrivateServiceCatalog> privateServiceCatalogList;

    @Override
    public void setFormattedPrivateServiceCatalogList(List<PrivateServiceCatalog> formattedPrivateServiceCatalogList) {
        this.formattedPrivateServiceCatalogList = formattedPrivateServiceCatalogList;
    }

    private  List<PrivateServiceCatalog> formattedPrivateServiceCatalogList;

    @Override
    public List<PrivateServiceCatalog> findAllPrivate() {
        getFormattedPrivateServiceCatalogList();
        return getTreeList(formattedPrivateServiceCatalogList);
    }

    @Override
    public PrivateServiceCatalog findPrivateBySn(String sn) {
        return privateServiceCatalogRepository.findPrivateBySn(sn);
    }

    @Override
    public PrivateServiceCatalog findPrivateByTitle(String title){
        List<PrivateServiceCatalog> catalogs = getPrivateServiceCatalogList();
        for(PrivateServiceCatalog catalog:catalogs){
            if(catalog.getTitle().equals(title))
                return catalog;
        }
        return null;
    }

    @Override
    public PrivateServiceCatalog savePrivate(PrivateServiceCatalog privateServiceCatalog) {
        setPrivateServiceCatalogList(null);
        setFormattedPrivateServiceCatalogList(null);
        privateServiceCatalog.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        privateServiceCatalog.setUpdatedAt(privateServiceCatalog.getCreatedAt());
        privateServiceCatalogRepository.savePrivate(privateServiceCatalog);
        return privateServiceCatalog;
    }

    @Override
    public PrivateServiceCatalog updatePrivate(PrivateServiceCatalog privateServiceCatalog) {
        setPrivateServiceCatalogList(null);
        setFormattedPrivateServiceCatalogList(null);
        privateServiceCatalog.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        privateServiceCatalogRepository.updatePrivate(privateServiceCatalog);
        return privateServiceCatalog;
    }

    @Override
    public void deletePrivate(PrivateServiceCatalog catalog) {
        setPrivateServiceCatalogList(null);
        setFormattedPrivateServiceCatalogList(null);
        privateServiceCatalogRepository.deletePrivate(catalog.getSn());
    }

    public List<PrivateServiceCatalog> getPrivateServiceCatalogList() {
        if(privateServiceCatalogList==null || privateServiceCatalogList.isEmpty()) {
            privateServiceCatalogList = privateServiceCatalogRepository.findAllPrivate();
        }
        return privateServiceCatalogList;
    }

    @Override
    public void setPrivateServiceCatalogList(List<PrivateServiceCatalog> privateServiceCatalogList) {
        this.privateServiceCatalogList = privateServiceCatalogList;
    }

    public List<PrivateServiceCatalog> getFormattedPrivateServiceCatalogList() {
        if(formattedPrivateServiceCatalogList==null || formattedPrivateServiceCatalogList.isEmpty()) {
            formattedPrivateServiceCatalogList = formatServiceCatalogs(getPrivateServiceCatalogList());
        }
        return formattedPrivateServiceCatalogList;
    }

    private List<PrivateServiceCatalog> formatServiceCatalogs(List<PrivateServiceCatalog> list){
        if(list == null || list.isEmpty())
            return list;
        for(PrivateServiceCatalog catalog:list){
            formatServiceCatalog(catalog);
        }
        return list;
    }

    private void formatServiceCatalog(PrivateServiceCatalog catalog){
        int level = catalog.getLevel()-1;
        String str = "";
        for(ServiceItem item:catalog.getItems()){
            str = "";
            for(int i=0;i<=level;i++)
                str = str +"--";
            item.setTitle(str+item.getTitle());
        }
        str = "";
        for(int i=0;i<level;i++)
            str = str +"--";
        catalog.setTitle(str+catalog.getTitle());

    }

    private List<PrivateServiceCatalog> getTreeList(List<PrivateServiceCatalog> list){
        //convert list to tree object
        List<PrivateServiceCatalog> treeList = new ArrayList<PrivateServiceCatalog>();
        for(PrivateServiceCatalog catalog:list) {
            if(catalog.getParentId() == null) {
                this.formatTreeList(treeList,catalog,list);
            }
        }
        return treeList;
    }

    private void formatTreeList(List<PrivateServiceCatalog> treeList,PrivateServiceCatalog catalog,List<PrivateServiceCatalog> children){
        treeList.add(catalog);
        for(PrivateServiceCatalog node1 : children){
            if(node1.getParentId() != null && node1.getParentId().equals(catalog.getId())){
                this.formatTreeList(treeList,node1,children);
            }
        }
    }
}
