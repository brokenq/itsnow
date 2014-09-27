/**
 * Developer: Kadvin Date: 14-9-15 上午10:44
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.ItsnowSchema;
import org.apache.ibatis.annotations.*;

/**
 * <h1>Itsnow Schema Repository</h1>
 */
public interface ItsnowSchemaRepository {
    @Select("SELECT * FROM itsnow_schemas WHERE name = #{name}")
    @ResultMap("schemaResult")
    ItsnowSchema findByName(@Param("name") String name);

    @Select("SELECT * FROM itsnow_schemas WHERE id = #{id}")
    @ResultMap("schemaResult")
    ItsnowSchema findById(@Param("id") long id);

    @Insert("INSERT INTO itsnow_schemas(host_id, name, configuration, description, created_at, updated_at)" +
            " VALUES(#{hostId}, #{name}, #{configuration, typeHandler=dnt.itsnow.util.PropertiesHandler}, #{description}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true)
    void create(ItsnowSchema schema);

    @Delete("DELETE FROM itsnow_schemas WHERE id = #{id}")
    void delete(ItsnowSchema schema);
}
