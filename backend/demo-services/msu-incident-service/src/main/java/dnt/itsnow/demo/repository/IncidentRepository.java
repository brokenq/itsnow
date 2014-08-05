package dnt.itsnow.demo.repository;

import dnt.itsnow.demo.model.Incident;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * Created by jacky on 2014/7/28.
 */
public interface IncidentRepository {


    @Select("select * from demo_incidents " +
            " order by #{orderBy} limit #{offset}, #{size}")
    List<Incident> findIncidents(@Param("orderBy") String orderBy,
                         @Param("offset") int offset,
                         @Param("size") int size);

    @Select("select * from demo_incidents where request_description like '%#{keyword}%'" +
            " order by #{orderBy} limit #{offset}, #{size}")
    List<Incident> findIncidentsByKeyword(@Param("keyword") String keyword,
                                                      @Param("orderBy") String orderBy,
                                                      @Param("offset") int offset,
                                                      @Param("size") int size);

    @Select("select * from demo_incidents where created_by like '#{orderBy}' " )
    //        " order by #{orderBy} limit #{offset}, #{size}")
    List<Incident> findIncidentsByUsername(@Param("username") String username,
                                          @Param("orderBy") String orderBy,
                                          @Param("offset") int offset,
                                          @Param("size") int size);

    @Select("select * from demo_incidents where created_by instance_id in '#{ids}'" +
            " order by #{orderBy} limit #{offset}, #{size}")
    List<Incident> findIncidentsByIds(@Param("ids") Set ids,
                                           @Param("orderBy") String orderBy,
                                           @Param("offset") int offset,
                                           @Param("size") int size);

    @Select("select count(0) from demo_incidents")
    int count();//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到

    @Select("select count(0) from demo_incidents where request_description like '%#{keyword}%'")
    int countByKeyword(String keyword);//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到

    @Select("select count(0) from demo_incidents")
    long countByUsername(@Param("username") String username);//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到


    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("INSERT INTO `demo_incidents` (`number`,`requester_name`,`requester_location`,`request_email`,`request_phone`," +
            "`service_catalog`,`category`,`impact`,`urgency`,`priority`,`request_type`," +
            "`ci_type`,`ci`,`request_description`,`created_at`,`updated_at`," +
            "`created_by`,`assigned_user`,`assigned_group`,`response_time`,`resolve_time`," +
            "`close_time`,`solution`)" +
            " VALUES (#{number},#{requesterName},#{requesterLocation},#{requestEmail},#{requestPhone}," +
            "#{serviceCatalog},#{category},#{impact},#{urgency},#{priority},#{requestType}," +
            "#{ciType},#{ci},#{requestDescription},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP," +
            "#{createdBy},#{assignedUser},#{assignedGroup},#{responseTime},#{resolveTime}," +
            "#{closeTime},#{solution});")
    void save(Incident incident);

    @Update("UPDATE 'demo_incidents' SET " +
            "`service_catalog` = #{serviceCatalog}," +
            "`category` = #{category}," +
            "`impact` = #{impact}," +
            "`urgency` = #{urgency}," +
            "`priority` = #{priority}," +
            "`ci_type` = #{ciType}," +
            "`ci` = #{ci}," +
            "`updated_at` = CURRENT_TIMESTAMP," +
            "`assigned_user` = #{assignedUser}," +
            "`assigned_group` = #{assignedGroup}," +
            "`solution` = #{solution}" +
            " WHERE `id` = #{id}")
    void update(Incident incident);


    @Update("UPDATE 'demo_incidents' SET " +
            "close_time = CURRENT_TIMESTAMP," +
            "close_code = #{closeCode} " +
            "WHERE id = #{id}")
    void close(Incident incident);



}
