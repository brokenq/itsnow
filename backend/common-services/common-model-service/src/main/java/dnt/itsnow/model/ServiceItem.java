/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

/**
 * <h1>服务项目</h1>
 *
 * 服务项目也不作为配置项
 *
 * TODO 添加测试用例
 */
public class ServiceItem extends Record{
    private String title;
    private String brief;
    private String description;
    private String icon;

    private ServiceCatalog catalog;

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

    public ServiceCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(ServiceCatalog catalog) {
        this.catalog = catalog;
    }
}
