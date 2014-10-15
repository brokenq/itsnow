package dnt.itsnow.model;

/**
 * <h1>Private Service Item Object</h1>
 */
public class PrivateServiceItem extends PublicServiceItem{

    private Long publicId;//公共服务项的ID，可考虑改为 PublicServiceItem对象

    public Long getPublicId() {
        return publicId;
    }

    public void setPublicId(Long publicId) {
        this.publicId = publicId;
    }
}
