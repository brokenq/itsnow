package dnt.itsnow.repository;

import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>部门管理持久层</h1>
 */
public interface DepartmentRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO departments (sn, name, parent_id, position, description, created_at, updated_at) VALUES " +
            "(#{sn}, #{name}, #{parentId}, #{position}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(Department department);

    @Delete("DELETE FROM departments WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE departments SET " +
            " sn                    = #{sn}, " +
            " name                  = #{name}, " +
            " parent_id             = #{parentId}," +
            " position              = #{position}," +
            " description           = #{description}," +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(Department department);

    public List<Department> findAll(@Param("keyword") String keyword);

    @Select("select * from departments where parent_id = #{parentId}")
    public List<Department> findAllByParentId(@Param("parentId") long parentId);

    public Department findBySn(@Param("sn") String sn);

    @Select("select * from departments where name = #{name}")
    Department findByName(String name);
}
