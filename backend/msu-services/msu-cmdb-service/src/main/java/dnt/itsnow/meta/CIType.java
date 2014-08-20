/**
 * Developer: Kadvin Date: 14-8-18 上午8:49
 */
package dnt.itsnow.meta;

import java.util.Set;

/**
 * CI(Configuration Item) Type
 * <ul>
 * <li>name: String 配置项类型名称
 * <li>parent: CIType 上级类型
 * <li>icon: Icon 图标
 * <li>description: String 该类型的描述
 * <li>relations: Set<CIRelationType>
 * <li>fields: Set<CIFieldType>
 * </ul>
 */
public class CIType {
    private String              name;
    private CIType              parent;
    private String              icon;
    private String              description;
    // 这个CIType可以/可能有的关系类型集合
    private Set<CIRelationType> relations;
    // 这个CIType中的字段
    private Set<CIFieldType>    fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CIType getParent() {
        return parent;
    }

    public void setParent(CIType parent) {
        this.parent = parent;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CIRelationType> getRelations() {
        return relations;
    }

    public void setRelations(Set<CIRelationType> relations) {
        this.relations = relations;
    }

    public Set<CIFieldType> getFields() {
        return fields;
    }

    public void setFields(Set<CIFieldType> fields) {
        this.fields = fields;
    }
}
