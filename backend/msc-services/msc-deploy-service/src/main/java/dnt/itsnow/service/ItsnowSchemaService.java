/**
 * Developer: Kadvin Date: 14-9-15 上午10:45
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.ItsnowSchemaException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;

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
    ItsnowSchema create(ItsnowSchema creating) throws ItsnowSchemaException;

    /**
     * <h2>删除特定的数据库schema</h2>
     *
     * @param schema 数据库schema对象
     */
    void delete(ItsnowSchema schema) throws ItsnowSchemaException;

    /**
     * <h2>根据名称查找到数据库schema对象</h2>
     *
     * @param name schema名称
     * @return 查找到的数据库schema对象
     */
    ItsnowSchema findByName(String name);


    /**
     * <h2>根据ID查找到数据库schema对象</h2>
     *
     * @param schemaId schemaID
     * @return 查找到的数据库schema对象
     */
    ItsnowSchema findById(long schemaId);

    /**
     * <h2>根据关键词查找Schema</h2>
     * @param keyword  Schema的关键词，可以为null
     * @param pageRequest 分页设置
     * @return 查找的结果
     */
    Page<ItsnowSchema> findAll(String keyword, PageRequest pageRequest);

    /**
     * 为即将部署在某个主机上的特定帐户的服务进程分配一个数据库schema实例
     * @param account 目标帐户
     * @param host 目标帐户的服务进程即将部署的主机
     * @return 新分配的schema（尚未创建）
     */
    ItsnowSchema pickSchema(Account account, ItsnowHost host) throws ItsnowHostException;
}
