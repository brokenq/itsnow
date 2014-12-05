/**
 * Developer: Kadvin Date: 14-8-18 上午9:23
 */
package dnt.itsnow.model;

import net.happyonroad.platform.model.Record;

/**
 * <h1>The Configuration Item</h1>
 *
 *  Managed Engine缺省的属性包括:
 * <ul>
 * <li>name:String 名称
 * <li>description 描述
 * 以下两个字段暂时不添加
 * <li>location    地点
 * <li>impact      业务影响
 * </ul>
 */
public class ConfigItem extends Record {
    private String name;
    private String description;
//    private String location;// refer to Model: Location
//    private String impact;  // refer to Dict:  Impact

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConfigItem that = (ConfigItem) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        //noinspection RedundantIfStatement
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + name  + "}";
    }
}
