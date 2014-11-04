/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

import javax.validation.constraints.NotNull;

/**
 * <h1>服务项目</h1>
 *
 * 服务项目也不作为配置项
 *
 */
public class ServiceItem extends Record{
    @NotNull
    private String title;
    private String brief;
    private String description;
    private String icon;
    @NotNull
    private String sn;
    //@NotNull
    //private ServiceCatalog catalog;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /*public ServiceCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(ServiceCatalog catalog) {
        this.catalog = catalog;
    }*/

}
