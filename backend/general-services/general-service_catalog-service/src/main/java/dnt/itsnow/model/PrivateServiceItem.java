package dnt.itsnow.model;

import javax.validation.constraints.NotNull;

/**
 * <h1>Private Service Item Object</h1>
 */
public class PrivateServiceItem extends ServiceItem{

    private Long publicId;//公共服务项的ID，可考虑改为 PublicServiceItem对象

    @NotNull
    private PrivateServiceCatalog catalog;

    public Long getPublicId() {
        return publicId;
    }

    public void setPublicId(Long publicId) {
        this.publicId = publicId;
    }

    public PrivateServiceCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(PrivateServiceCatalog catalog) {
        this.catalog = catalog;
    }
}
