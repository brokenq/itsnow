/**
 * Developer: Kadvin Date: 14-9-15 上午10:44
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.platform.util.PageRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h1>Itsnow Schema Repository</h1>
 */
public interface ItsnowSchemaRepository {
    ItsnowSchema findByName(@Param("name") String name);

    ItsnowSchema findById(@Param("id") long id);

    @Insert("INSERT INTO itsnow_schemas(host_id, name, configuration, description, created_at, updated_at)" +
            " VALUES(#{hostId}, #{name}, #{configuration, typeHandler=dnt.itsnow.util.PropertiesHandler}, #{description}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true)
    void create(ItsnowSchema schema);

    @Delete("DELETE FROM itsnow_schemas WHERE id = #{id}")
    void delete(ItsnowSchema schema);

    int countByKeyword(@Param("keyword") String keyword);

    List<ItsnowSchema> findAllByKeyword(@Param("keyword") String keyword, @Param("pageRequest") PageRequest pageRequest);
}
