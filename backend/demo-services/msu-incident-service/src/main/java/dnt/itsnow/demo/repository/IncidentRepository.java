package dnt.itsnow.demo.repository;

import dnt.itsnow.demo.model.Incident;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

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

    @Select("select * from demo_incidents where instance_id = #{id}")
    Incident findByInstanceId(@Param("id") String id);

    List<Incident> findAllByInstanceIds(@Param("ids")List<String> ids,@Param("pageable")Pageable pageable);

    @Select("select count(0) from demo_incidents")
    int count();//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到

    @Select("select count(0) from demo_incidents where request_description like '%#{keyword}%'")
    int countByKeyword(String keyword);//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到

    @Select("select count(0) from demo_incidents")
    long countByUsername(@Param("username") String username);//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到


    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("INSERT INTO `demo_incidents` (`number`,`instance_id`,`requester_name`,`requester_location`,`requester_email`,`requester_phone`," +
            "`service_catalog`,`category`,`impact`,`urgency`,`priority`,`request_type`," +
            "`ci_type`,`ci`,`request_description`,`created_at`,`updated_at`," +
            "`created_by`,`assigned_user`,`assigned_group`,`response_time`,`resolve_time`," +
            "`close_time`,`solution`,`close_code`)" +
            " VALUES (#{number},#{instanceId},#{requesterName},#{requesterLocation},#{requesterEmail},#{requesterPhone}," +
            "#{serviceCatalog},#{category},#{impact},#{urgency},#{priority},#{requestType}," +
            "#{ciType},#{ci},#{requestDescription},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP," +
            "#{createdBy},#{assignedUser},#{assignedGroup},#{responseTime},#{resolveTime}," +
            "#{closeTime},#{solution},#{closeCode});")
    void save(Incident incident);

    @Update("UPDATE demo_incidents SET " +
            "`requester_location` = #{requesterLocation},`requester_name` = #{requesterName},"+
            "`requester_email` = #{requesterEmail},`requester_phone` = #{requesterPhone},"+
            "`request_type` = #{requestType},"+
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
            "`response_time` = #{responseTime},"+
            "`resolve_time` = #{resolveTime},"+
            "`close_time` = #{closeTime},"+
            "`solution` = #{solution},"+
            "`status` = #{status},"+
            "`close_code` = #{closeCode} "+
            " WHERE `id` = #{id};")
    void update(Incident incident);


    @Update("UPDATE 'demo_incidents' SET " +
            "close_time = CURRENT_TIMESTAMP," +
            "close_code = #{closeCode} " +
            "WHERE id = #{id}")
    void close(Incident incident);



}
