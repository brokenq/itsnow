package dnt.itsnow.model;

import java.util.List;

/**
 * <h1>Private Service Catalog Object</h1>
 */
public class PrivateServiceCatalog extends ServiceCatalog {

    private Long publicId;//公共服务目录ID，可考虑改为PublicServiceCatalog对象

    public Long getPublicId() {
        return publicId;
    }

    public void setPublicId(Long publicId) {
        this.publicId = publicId;
    }

    private List<PrivateServiceItem> items;

    public List<PrivateServiceItem> getItems() {
        return items;
    }

    public void setItems(List<PrivateServiceItem> items) {
        this.items = items;
    }

    public ServiceItem getItemBySn(String sn) {
        if( items == null ) return null;
        for (ServiceItem item : items) {
            if( item.getSn().equals(sn) ) return item;
        }
        return null;
    }
}
