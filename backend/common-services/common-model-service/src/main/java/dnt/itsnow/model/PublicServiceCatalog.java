/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import java.util.List;

/**
 * <h1>公共的服务目录</h1>
 */
public class PublicServiceCatalog extends ServiceCatalog{

    private List<PublicServiceItem> items;

    public List<PublicServiceItem> getItems() {
        return items;
    }

    public void setItems(List<PublicServiceItem> items) {
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
