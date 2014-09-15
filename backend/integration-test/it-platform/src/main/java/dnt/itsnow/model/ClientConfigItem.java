/**
 * Developer: Kadvin Date: 14-9-15 下午1:28
 */
package dnt.itsnow.model;

/**
 * <h1>对应于Record的Client Json解析对象</h1>
 */
public class ClientConfigItem {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
