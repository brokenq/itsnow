package dnt.itsnow.repository;

import dnt.itsnow.model.WorkTime;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>类功能说明</h1>
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

    @Select("select count(0) from work_times")
    public int count();

    @Select("select * from work_times order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<WorkTime> find(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from work_times where name like #{keyword} or sn like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    @Select("select * from work_times where name like #{keyword} or sn like #{keyword}" +
            " order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<WorkTime> findByKeyword(@Param("keyword") String keyword,
                                                 @Param("sort") String sort,
                                                 @Param("dir") String dir,
                                                 @Param("offset") int offset,
                                                 @Param("size") int size);

    @Select("SELECT * FROM work_times WHERE sn = #{sn}")
    public WorkTime findBySn(@Param("sn") String sn);
}
