package dnt.itsnow.model;

/**
 * <h1>Private Service Catalog Object</h1>
 */
public class PrivateServiceCatalog extends PublicServiceCatalog {

    private Long publicId;//公共服务目录ID，可考虑改为PublicServiceCatalog对象

    public Long getPublicId() {
        return publicId;
    }

    public void setPublicId(Long publicId) {
        this.publicId = publicId;
    }
}
