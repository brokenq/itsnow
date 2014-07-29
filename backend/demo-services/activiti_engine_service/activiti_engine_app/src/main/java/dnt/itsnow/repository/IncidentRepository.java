package dnt.itsnow.repository;

import dnt.itsnow.model.Incident;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by jacky on 2014/7/28.
 */
public interface IncidentRepository {


    @Select("select * from demo_incidents " +
            " order by #{orderBy} limit #{offset}, #{size}")
    List<Incident> findIncidents(@Param("orderBy") String orderBy,
                         @Param("offset") int offset,
                         @Param("size") int size);

    @Select("select count(0) from demo_incidents")
    int count();//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("insert into demo_incidents(topic, solution1, solution2, resolved, close_code,owner,status,created_at, updated_at) " +
            "values(#{topic}, #{solution1}, #{solution2}, #{resolved}, #{closeCode},#{owner},#{status}, #{createdAt}, #{updatedAt})")
    void save(Incident incident);

}
