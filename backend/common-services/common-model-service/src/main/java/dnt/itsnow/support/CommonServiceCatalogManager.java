package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.model.ServiceCatalog;
import dnt.itsnow.model.ServiceItem;
import dnt.itsnow.repository.CommonServiceCatalogRepository;
import dnt.itsnow.repository.CommonServiceItemRepository;
import dnt.itsnow.service.CommonServiceCatalogService;
import net.happyonroad.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1>Common ServiceCatalog Manager</h1>
 */
@Service
@Transactional
public class CommonServiceCatalogManager extends Bean implements CommonServiceCatalogService {

    @Autowired
    CommonServiceCatalogRepository commonServiceCatalogRepository;

    @Autowired
    CommonServiceItemRepository commonServiceItemRepository;

    private  List<PublicServiceCatalog> commonServiceCatalogList;

    private  List<PublicServiceCatalog> formattedServiceCatalogList;

    @Override
    public List<PublicServiceCatalog> findAll() {
        getFormattedServiceCatalogList();
        return getTreeList(formattedServiceCatalogList);
    }

    private List<PublicServiceCatalog> getTreeList(List<PublicServiceCatalog> list){
        //convert list to tree object
        List<PublicServiceCatalog> treeList = new ArrayList<PublicServiceCatalog>();
        for(PublicServiceCatalog catalog:list) {
            if(catalog.getParentId() == null) {
                this.formatTreeList(treeList,catalog,list);
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
        int level = catalog.getLevel()-1;
        String str = "";
        if(catalog.getItems() != null){
            for(ServiceItem item:catalog.getItems()){
                str = "";
                for(int i=0;i<=level;i++)
                    str = str +"--";
                item.setTitle(str+item.getTitle());
            }
        }
        str = "";
        for(int i=0;i<level;i++)
            str = str +"--";
        catalog.setTitle(str+catalog.getTitle());
    }

    @Override
    public PublicServiceCatalog findBySn(String sn) {
        return commonServiceCatalogRepository.findBySn(sn);
    }

    @Override
    public PublicServiceCatalog findById(Long id) {
        List<PublicServiceCatalog> list = getCommonServiceCatalogList();
        for(PublicServiceCatalog catalog:list){
            if(catalog.getId() == id)
                return catalog;
        }
        return null;
    }

    public List<PublicServiceCatalog> getCommonServiceCatalogList() {
        if(commonServiceCatalogList==null || commonServiceCatalogList.isEmpty()) {
            commonServiceCatalogList = commonServiceCatalogRepository.findAll();
        }
        return commonServiceCatalogList;
    }

    @Override
    public void setFormattedServiceCatalogList(List<PublicServiceCatalog> formattedServiceCatalogList) {
        this.formattedServiceCatalogList = formattedServiceCatalogList;
    }

    @Override
    public List<PublicServiceCatalog> findByAccountId(Long accountId) {
        List<PublicServiceItem> items = commonServiceItemRepository.findByAccountId(accountId);
        List<PublicServiceCatalog> catalogs = commonServiceCatalogRepository.findByAccountId(accountId);
        List<PublicServiceCatalog> treeCatalogs = this.getTreeList(catalogs);
        for(PublicServiceItem item:items){
            for(PublicServiceCatalog catalog:treeCatalogs){
                if(item.getCatalog().getId() == catalog.getId()) {
                    if(catalog.getItems() == null){
                        List<PublicServiceItem> is = new ArrayList<PublicServiceItem>();
                        catalog.setItems(is);
                    }
                    catalog.getItems().add(item);
                    break;
                }
            }
        }
        formatServiceCatalogs(treeCatalogs);
        return treeCatalogs;
    }

    @Override
    public PublicServiceCatalog findByTitle(String title) {
        List<PublicServiceCatalog> list = getCommonServiceCatalogList();
        for(PublicServiceCatalog catalog:list){
            if(catalog.getTitle().equals(title))
                return catalog;
        }
        return null;
    }

    public List<PublicServiceCatalog> getFormattedServiceCatalogList() {
        if(formattedServiceCatalogList==null || formattedServiceCatalogList.isEmpty()) {
            formattedServiceCatalogList = formatServiceCatalogs(getCommonServiceCatalogList());
        }
        return formattedServiceCatalogList;
    }

    @Override
    public void setCommonServiceCatalogList(List<PublicServiceCatalog> commonServiceCatalogList) {
        this.commonServiceCatalogList = commonServiceCatalogList;
    }
    private List<ServiceCatalog> buildTreeTable(List<ServiceCatalog> serviceCatalogs) {
        List<ServiceCatalog> treeTable = new LinkedList<ServiceCatalog>();

        for (ServiceCatalog psi : serviceCatalogs) {
            treeTable.add(psi);
            loop(treeTable, psi);
        }
        return treeTable;
    }

    private void loop(List<ServiceCatalog> treeTable, ServiceCatalog psi) {
        if (psi.getChildren() != null && psi.getChildren().size() > 0) {
            for (ServiceCatalog child : psi.getChildren()) {
                treeTable.add(child);
                loop(treeTable, child);
            }
            psi.setChildren(null);
        }
    }
    @Override
    public List<ServiceCatalog> findCatalogsBySn(String sn) {
        ServiceCatalog  serviceCatalog=commonServiceCatalogRepository.findBySn(sn);
        List<ServiceCatalog> list=new ArrayList<ServiceCatalog>();
        list.add(serviceCatalog);
        list.addAll(buildTreeTable(serviceCatalog.getChildren()));
        for (ServiceCatalog psi : list) {
            formatServiceCatalog((PublicServiceCatalog) psi);
        }
       return list;

    }
}
