package dnt.itsnow.repository;

import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>工作时间持久层</h1>
 */
public interface WorkTimeRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO work_times (sn, name, work_days, start_at, end_at, description, created_at, updated_at) VALUES " +
            "(#{sn}, #{name}, #{workDays}, #{startAt}, #{endAt}, #{description} ,#{createdAt}, #{updatedAt})")
    public void create(WorkTime workTime);

    @Delete("DELETE FROM work_times WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE work_times SET " +
            " sn          = #{sn}, " +
            " name        = #{name}, " +
            " work_days   = #{workDays}," +
            " start_at  = #{startAt}," +
            " end_at    = #{endAt}," +
            " description = #{description}, " +
            " created_at  = #{createdAt}, " +
            " updated_at  = #{updatedAt} " +
            " WHERE id    = #{id} ")
    public void update(WorkTime workTime);

    public int count(@Param("keyword") String keyword);

    public List<WorkTime> findAll(
            @Param("keyword") String keyword,
            @Param("pageable") Pageable pageable);

    public WorkTime findBySn(@Param("sn") String sn);

}
