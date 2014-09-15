/**
 * Developer: Kadvin Date: 14-9-15 上午10:45
 */
package dnt.itsnow.service;

import dnt.itsnow.model.ItsnowSchema;

/**
 * <h1>Itsnow Schema Service</h1>
 */
public interface ItsnowSchemaService {
    /**
     * <h2>创建一个应用的数据库 schema</h2>
     *
     * @param creating 被创建的schema
     * @return 创建成功之后的schema
     */
    ItsnowSchema create(ItsnowSchema creating);

    /**
     * <h2>删除特定的数据库schema</h2>
     *
     * @param schema 数据库schema对象
     */
    void delete(ItsnowSchema schema);

    /**
     * <h2>根据名称查找到数据库schema对象</h2>
     *
     * @param name schema名称
     * @return 查找到的数据库schema对象
     */
    ItsnowSchema findByName(String name);


}
