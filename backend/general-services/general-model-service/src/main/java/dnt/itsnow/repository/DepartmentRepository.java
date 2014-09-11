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
    @Insert("INSERT INTO departments (sn, name, parent_id, description, created_at, updated_at) VALUES " +
            "(#{sn}, #{name}, #{parent.id}, #{description}, #{createdAt}, #{updatedAt})")
    public void create(Department department);

    @Delete("DELETE FROM departments WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE departments SET " +
            " sn                    = #{sn}, " +
            " name                  = #{name}, " +
            " parent_id             = #{parent.id}," +
            " description           = #{description}," +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(Department department);

    @Select("select count(0) from departments")
    public int count();

//    @Select("select * from departments order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<Department> find(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from departments where name like #{keyword} or sn like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

//    @Select("select * from departments where name like #{keyword} or sn like #{keyword}" +
//            " order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<Department> findByKeyword(@Param("keyword") String keyword,
                                    @Param("sort") String sort,
                                    @Param("dir") String dir,
                                    @Param("offset") int offset,
                                    @Param("size") int size);

    public Department findBySn(@Param("sn") String sn);
}
