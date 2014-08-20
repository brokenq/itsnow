/**
 * Developer: Kadvin Date: 14-8-18 上午8:49
 */
package dnt.itsnow.meta;

/**
 * CI(Configuration Item) Field Type
 * <ul>
 * <li>name: 字段名称
 * <li>type: 字段数值类型
 * <li>section: 该字段所属分组（暂时没有定义CISection)
 * </ul>
 */
public class CIFieldType {
    private String name;
    private String type;
    private String section;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
