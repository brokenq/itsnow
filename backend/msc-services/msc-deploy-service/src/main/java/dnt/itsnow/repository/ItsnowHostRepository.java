/**
 * Developer: Kadvin Date: 14-9-15 上午10:44
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.util.PageRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>Itsnow Host Repository</h1>
 */
public interface ItsnowHostRepository {
    int countByKeyword(@Param("keyword") String keyword);
    // 默认语句不需要对varchar字符串做uppper/lower转换
    // Mysql默认charset = utf8, collation为utf_general_ci, 大小写不敏感
    List<ItsnowHost> findAllByKeyword(@Param("keyword") String keyword,
                                      @Param("pageRequest") PageRequest pageRequest);

    @Select("SELECT * FROM itsnow_hosts WHERE name != 'srv1.itsnow.com'")
    @ResultMap("hostResult")
    List<ItsnowHost> findAllDbHosts();

    @Select("SELECT * FROM itsnow_hosts WHERE address = #{address}")
    @ResultMap("hostResult")
    ItsnowHost findByAddress(@Param("address")String address);

    @Select("SELECT * FROM itsnow_hosts WHERE name = #{name}")
    @ResultMap("hostResult")
    ItsnowHost findByName(@Param("name") String name);

    @Select("SELECT * FROM itsnow_hosts WHERE id = #{id}")
    @ResultMap("hostResult")
    ItsnowHost findById(@Param("id")Long id);

    @Insert("INSERT INTO itsnow_hosts(name, address, type, capacity, status, configuration, description, created_at, updated_at) " +
            "VALUES (#{name}, #{address}, #{type}, #{capacity}, #{status}, #{configuration,typeHandler=dnt.itsnow.util.PropertiesHandler}, #{description}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true)
    void create(ItsnowHost creating);

    @Delete("DELETE FROM itsnow_hosts WHERE address = #{address}")
    void deleteByAddress(@Param("address")String address);


    @Update("UPDATE itsnow_hosts SET " +
            "  name          = #{name}, " +
            "  address       = #{address}, " +
            "  type          = #{type}, " +
            "  capacity      = #{capacity}, " +
            "  configuration = #{configuration,typeHandler=dnt.itsnow.util.PropertiesHandler}, " +
            "  description   = #{description}, " +
            "  status        = #{status}, " +
            "  updated_at    = #{updatedAt} " +
            "WHERE id = #{id}")
    void update(ItsnowHost host);

    @Select("SELECT * FROM itsnow_hosts WHERE configuration REGEXP '\"${name}\" *: *\"?${value}\"?' limit 1")
    @ResultMap("hostResult")
    ItsnowHost findByConfiguration(@Param("name")String name, @Param("value")String value);

    @Select("SELECT * FROM itsnow_hosts WHERE configuration REGEXP '\"${name}\" *: *\"?${value}\"?'")
    @ResultMap("hostResult")
    List<ItsnowHost> findAllByConfiguration(@Param("name")String name, @Param("value") String value);

    // 统计 主机关联的进程和Schema的 数量总和
    @Select("SELECT (SELECT COUNT(p.id) FROM itsnow_processes p WHERE p.host_id = h.id) " +
            "      +(SELECT COUNT(s.id) FROM itsnow_schemas s WHERE s.host_id = h.id)   " +
            "FROM itsnow_hosts h WHERE h.id = #{id}" )
    int countLinked(@Param("id") long id);
}
