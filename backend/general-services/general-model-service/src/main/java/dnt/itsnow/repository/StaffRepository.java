package dnt.itsnow.repository;

import dnt.itsnow.model.Site;
import dnt.itsnow.model.Staff;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>地点类持久层</h1>
 */
public interface StaffRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO staffs (no, name, mobile_phone, fixed_phone, email, title, type, status, description, user_id, site_id, dept_id, created_at, updated_at) VALUES " +
            "(#{no}, #{name}, #{mobilePhone}, #{fixedPhone}, #{email}, #{title}, #{type}, #{status}, #{description}, #{user.id}, #{site.id}, #{department.id}, #{createdAt}, #{updatedAt})")
    public void create(Staff staff);

    @Delete("DELETE FROM staffs WHERE no = #{no}")
    public void delete(@Param("no") String no);

    @Update("UPDATE staffs SET " +
            " no           = #{no}, " +
            " name         = #{name}, " +
            " mobile_phone = #{mobilePhone}," +
            " fixed_phone  = #{fixedPhone}," +
            " email        = #{email}," +
            " title        = #{title}, " +
            " type         = #{type}, " +
            " status       = #{status}, " +
            " description  = #{description}, " +
            " user_id      = #{user.id}, " +
            " site_id      = #{site.id}, " +
            " dept_id      = #{department.id}, " +
            " created_at   = #{createdAt}, " +
            " updated_at   = #{updatedAt} " +
            " WHERE id     = #{id} ")
    public void update(Staff staff);

    @Select("select count(0) from staffs")
    public int count();

    public List<Staff> find(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from staffs where name like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    public List<Staff> findByKeyword(@Param("keyword") String keyword,
                                    @Param("sort") String sort,
                                    @Param("dir") String dir,
                                    @Param("offset") int offset,
                                    @Param("size") int size);

    public Staff findByNo(@Param("no") String no);
}
