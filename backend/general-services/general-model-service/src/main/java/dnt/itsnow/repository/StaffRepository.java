package dnt.itsnow.repository;

import dnt.itsnow.model.Staff;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>员工管理持久层</h1>
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

    public int count(@Param("keyword") String keyword);

    public List<Staff> findAll(
            @Param("keyword") String keyword,
            @Param("pageable") Pageable pageable);

    public Staff findByNo(@Param("no") String no);
}
