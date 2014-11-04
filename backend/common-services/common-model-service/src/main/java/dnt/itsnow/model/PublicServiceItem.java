/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import javax.validation.constraints.NotNull;

/**
 * <h1>公共的服务项目</h1>
 */
public class PublicServiceItem extends ServiceItem {

    /*@Override
    public PublicServiceCatalog getCatalog() {
        return (PublicServiceCatalog) super.getCatalog();
    }*/


    @NotNull
    private PublicServiceCatalog catalog;

    public PublicServiceCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(PublicServiceCatalog catalog) {
        this.catalog = catalog;
    }
}
