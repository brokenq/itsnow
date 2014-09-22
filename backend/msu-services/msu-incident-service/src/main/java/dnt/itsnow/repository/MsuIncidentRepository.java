package dnt.itsnow.repository;

import dnt.itsnow.model.Incident;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jacky on 2014/7/28.
 */
public interface MsuIncidentRepository {

    @Select("SELECT * FROM msu_incidents WHERE msu_instance_id = #{id}")
    Incident findByInstanceId(@Param("id") String id);

    List<Incident> findAllByInstanceIds(@Param("ids")List<String> ids,
                                        @Param("keyword")String keyword,
                                        @Param("pageable")Pageable pageable);

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("INSERT INTO msu_incidents (number,msu_instance_id,requester_name,requester_location,requester_email,requester_phone," +
            "service_catalog,category,impact,urgency,priority,request_type," +
            "ci_type,ci,request_description,created_at,updated_at," +
            "created_by,updated_by,assigned_user,assigned_group,response_time,resolve_time," +
            "close_time,solution,close_code,msu_status,msu_account_name)" +
            " VALUES (#{number},#{msuInstanceId},#{requesterName},#{requesterLocation},#{requesterEmail},#{requesterPhone}," +
            "#{serviceCatalog},#{category},#{impact},#{urgency},#{priority},#{requestType}," +
            "#{ciType},#{ci},#{requestDescription},#{createdAt},#{updatedAt}," +
            "#{createdBy},#{updatedBy},#{assignedUser},#{assignedGroup},#{responseTime},#{resolveTime}," +
            "#{closeTime},#{solution},#{closeCode},#{msuStatus},#{msuAccountName});")
    void create(Incident incident);

    @Update("UPDATE msu_incidents SET " +
            "requester_location = #{requesterLocation},requester_name = #{requesterName},"+
            "requester_email = #{requesterEmail},requester_phone = #{requesterPhone},"+
            "request_type = #{requestType},"+
            "service_catalog = #{serviceCatalog}," +
            "category = #{category}," +
            "impact = #{impact}," +
            "urgency = #{urgency}," +
            "priority = #{priority}," +
            "ci_type = #{ciType}," +
            "ci = #{ci}," +
            "updated_at = #{updatedAt}," +
            "updated_by = #{updatedBy}," +
            "assigned_user = #{assignedUser}," +
            "assigned_group = #{assignedGroup}," +
            "response_time = #{responseTime},"+
            "resolve_time = #{resolveTime},"+
            "close_time = #{closeTime},"+
            "solution = #{solution},"+
            "msu_status = #{msuStatus},"+
            "msp_status = #{mspStatus},"+
            "close_code = #{closeCode}, "+
            "msp_account_name = #{mspAccountName}, "+
            "msp_instance_id = #{mspInstanceId}, "+
            "can_process = #{canProcess}, "+
            "resolved = #{resolved}, "+
            "hardware_error = #{hardwareError}, "+
            "close_code = #{closeCode} "+
            " WHERE msu_instance_id = #{msuInstanceId};")
    void update(Incident incident);

}
