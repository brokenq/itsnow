/**
 * Developer: Kadvin Date: 14-8-18 上午9:23
 */
package dnt.itsnow.model;

import dnt.itsnow.meta.CIType;

/**
 * The Configuration Item
 * <ul>
 * <li>type: CIType
 * <li>name:String 显示名称
 * <li>asset: Boolean 是否作为资产
 * <li>assetCategory: AssetCategory
 * <li>assetCatalog: AssetCatalog: 资产类型
 * <li>description, impact, site等字段是否需要待细化
 * <li>attributes: Map<String, ?> 实例扩展属性
 * </ul>
 */
public class ConfigItem {
    private CIType type;
    private String name;
    private String description;
    private boolean asset;

}
