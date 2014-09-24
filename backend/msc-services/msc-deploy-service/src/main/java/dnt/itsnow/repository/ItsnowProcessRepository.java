/**
 * Developer: Kadvin Date: 14-9-15 上午10:44
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.platform.util.PageRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>Itsnow Process Repository</h1>
 */
public interface ItsnowProcessRepository {
    int countByKeyword(@Param("keyword") String keyword);

    List<ItsnowProcess> findAllByKeyword(@Param("keyword") String keyword,
                                         @Param("pageRequest") PageRequest pageRequest);

    //FIND 出来带上 host, schema, account等关联对象
    ItsnowProcess findByName(@Param("name") String name);

    @Insert("INSERT INTO itsnow_processes(" +
            "    account_id, " +
            "    host_id, " +
            "    schema_id, " +
            "    name, " +
            "    pid, " +
            "    wd, " +
            "    configuration, " +
            "    description, " +
            "    status, " +
            "    created_at, " +
            "    updated_at" +
            ") VALUES(" +
            "    #{accountId}, " +
            "    #{hostId}, " +
            "    #{schemaId}, " +
            "    #{name}, " +
            "    #{pid}, " +
            "    #{wd}, " +
            "    #{configuration, typeHandler=dnt.itsnow.util.PropertiesHandler}, " +
            "    #{description}, " +
            "    #{status}, " +
            "    #{createdAt}, " +
            "    #{updatedAt}" +
            ")")
    @Options(useGeneratedKeys = true)
    void create(ItsnowProcess process);

    @Delete("DELETE FROM itsnow_processes WHERE name = #{name}")
    void deleteByName(@Param("name") String name);

    @Update("UPDATE itsnow_processes SET " +
            "  name          = #{name}, " +
            "  pid           = #{pid}, " +
            "  wd            = #{wd}, " +
            "  configuration = #{configuration,typeHandler=dnt.itsnow.util.PropertiesHandler}, " +
            "  description   = #{description}, " +
            "  status        = #{status}, " +
            "  updated_at    = #{updatedAt} " +
            "WHERE id = #{id}")
    void update(ItsnowProcess process);

    @Select("SELECT * FROM itsnow_processes WHERE configuration REGEXP '\"${name}\" *: *\"?${value}\"?' limit 1")
    @ResultMap("basicProcessResult")  //TODO processResult 会在 accountType 上出问题，待解决
    ItsnowProcess findByConfiguration(@Param("name")String name, @Param("value")String value);
}
