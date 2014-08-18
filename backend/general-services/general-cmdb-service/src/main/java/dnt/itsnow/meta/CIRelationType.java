/**
 * Developer: Kadvin Date: 14-8-18 上午8:49
 */
package dnt.itsnow.meta;

/**
 * CI(Configuration Item) Relation Type
 *
 * <ul>
 * <li>name: String 关系名称，英文，中文名称由国际化机制支持
 * <li>reverse_name: String 反向关系名称，亦是英文
 * <li>sourceRule: Rule 这种关系对源对象的类型限制（仅做类型限制）
 * <li>destRule: Rule 这种关系对目的对象的类型限制
 * <li>css: 关系展示时的显示特性，如是否显示箭头，线形，粗细，虚实等
 * <li>description: 此关系类型的说明
 * </ul>
 *
 * 根据实际开发和其他参考经验，Relation的定义应该不仅仅限于CI之间，还应该可以在CI和非CI之间
 */
public class CIRelationType {

    private String name;
    private String reverseName;
    private CIFilter sourceRule;
    private CIFilter destRule;
    private String css;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReverseName() {
        return reverseName;
    }

    public void setReverseName(String reverseName) {
        this.reverseName = reverseName;
    }

    public CIFilter getSourceRule() {
        return sourceRule;
    }

    public void setSourceRule(CIFilter sourceRule) {
        this.sourceRule = sourceRule;
    }

    public CIFilter getDestRule() {
        return destRule;
    }

    public void setDestRule(CIFilter destRule) {
        this.destRule = destRule;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
