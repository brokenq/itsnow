/**
 * Developer: Kadvin Date: 14-9-15 上午10:44
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.HostType;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.util.PageRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>Itsnow Host Repository</h1>
 */
public interface ItsnowHostRepository {
    int countByKeyword(@Param("keyword") String keyword);
    // 默认语句不需要对varchar字符串做upper/lower转换
    // Mysql默认charset = utf8, collation为utf_general_ci, 大小写不敏感
    List<ItsnowHost> findAllByKeyword(@Param("keyword") String keyword,
                                      @Param("pageRequest") PageRequest pageRequest);

    List<ItsnowHost> findAllByType(@Param("type") HostType hostType);

    ItsnowHost findByAddress(@Param("address")String address);

    ItsnowHost findByIdAndAddress(@Param("id") Long id, @Param("address") String address);

    ItsnowHost findByName(@Param("name") String name);

    ItsnowHost findByIdAndName(@Param("id") Long id, @Param("name") String name);

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

    ItsnowHost findByConfiguration(@Param("name")String name, @Param("value")String value);

    List<ItsnowHost> findAllByConfiguration(@Param("name")String name, @Param("value") String value);

    List<ItsnowHost> findAll();
}
