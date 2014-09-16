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

    List<ItsnowHost> findAllByKeyword(@Param("keyword") String keyword,
                                      @Param("pageRequest") PageRequest pageRequest);

    @Select("SELECT * FROM itsnow_hosts WHERE name != 'srv1.itsnow.com'")
    @ResultMap("hostResult")
    List<ItsnowHost> findAllDbHosts();

    @Select("SELECT * FROM itsnow_hosts WHERE address = #{address}")
    @ResultMap("hostResult")
    ItsnowHost findByAddress(String address);

    @Select("SELECT * FROM itsnow_hosts WHERE id = #{id}")
    @ResultMap("hostResult")
    ItsnowHost findById(Integer id);

    @Insert("INSERT INTO itsnow_hosts(name, address, capacity, status, configuration, description, created_at, updated_at) " +
            "VALUES (#{name}, #{address}, #{capacity}, #{status}, #{configuration,typeHandler=dnt.itsnow.util.PropertiesHandler}, #{description}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true)
    void create(ItsnowHost creating);

    @Delete("DELETE FROM itsnow_hosts WHERE address = #{address}")
    void deleteByAddress(String address);
}
