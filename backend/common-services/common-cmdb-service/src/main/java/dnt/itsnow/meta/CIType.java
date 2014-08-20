/**
 * Developer: Kadvin Date: 14-8-18 上午8:49
 */
package dnt.itsnow.meta;

import dnt.itsnow.platform.model.Record;

import java.util.HashSet;
import java.util.Set;

/**
 * CI(Configuration Item) Type
 * <ul>
 * <li>name: String 配置项类型名称
 * <li>parent: CIType 上级类型
 * <li>icon: Icon 图标
 * <li>description: String 该类型的描述
 * <li>leftRelations: Set<CIRelationType>: 作为左对象的关系，包括无方向关系
 * <li>rightRelations: Set<CIRelationType>: 作为右对象的关系，包括无方向关系
 * <li>relations: Set<CIRelationType>: 全部关系
 * <li>fields: Set<CIField>
 * </ul>
 *
 * CI Type还可以指定
 * <li>asset: Boolean 是否作为资产
 * <li>assetCategory: AssetCategory
 * <li>assetType: AssetType: 资产类型
 */
public class CIType extends Record {
    private String              name;
    private CIType              parent;
    private String              icon;
    private String              description;
    // 这个CIType可以/可能有的关系类型集合
    private Set<CIRelationType> leftRelations;
    private Set<CIRelationType> rightRelations;
    // 这个CIType中的字段，如何映射？
    private Set<CIField>    fields;

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
        Set<CIRelationType> relations = new HashSet<CIRelationType>();
        if( this.leftRelations != null ) relations.addAll(leftRelations);
        if( this.rightRelations != null ) relations.addAll(rightRelations);
        return relations;
    }

    public Set<CIRelationType> getLeftRelations() {
        return leftRelations;
    }

    public void setLeftRelations(Set<CIRelationType> leftRelations) {
        this.leftRelations = leftRelations;
    }

    public Set<CIRelationType> getRightRelations() {
        return rightRelations;
    }

    public void setRightRelations(Set<CIRelationType> rightRelations) {
        this.rightRelations = rightRelations;
    }

    public Set<CIField> getFields() {
        return fields;
    }

    public void setFields(Set<CIField> fields) {
        this.fields = fields;
    }
}
