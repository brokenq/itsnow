package dnt.itsnow.repository;

import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>地点类持久层</h1>
 */
public interface DepartmentRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO departments (sn, name, parent_id, position, description, created_at, updated_at) VALUES " +
            "(#{sn}, #{name}, #{parent.id}, #{position}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(Department department);

    @Delete("DELETE FROM departments WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE departments SET " +
            " sn                    = #{sn}, " +
            " name                  = #{name}, " +
            " parent_id             = #{parent.id}," +
            " position              = #{position}," +
            " description           = #{description}," +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(Department department);

    @Select("SELECT * FROM departments")
    public List<Department> findAll();

    public Department findBySn(@Param("sn") String sn);

}
