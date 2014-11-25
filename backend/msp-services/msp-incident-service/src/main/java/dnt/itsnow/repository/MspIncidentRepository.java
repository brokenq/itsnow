package dnt.itsnow.repository;

import dnt.itsnow.model.Incident;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>Msp Incident Repository</h1>
 */
public interface MspIncidentRepository {

    @Select("SELECT * FROM msp_incidents WHERE msp_instance_id = #{id}")
    Incident findByInstanceId(@Param("id") String id);

    List<Incident> findAllByInstanceIds(@Param("ids")List<String> ids,@Param("keyword")String keyword,@Param("pageable")Pageable pageable);

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("INSERT INTO msp_incidents (number,msu_instance_id,msp_instance_id,requester_name,requester_location,requester_email,requester_phone," +
            "service_catalog,category,impact,urgency,priority,request_type," +
            "ci_type,ci,request_description,created_at,updated_at," +
            "created_by,updated_by,assigned_user,assigned_group,response_time,resolve_time," +
            "close_time,solution,close_code,msu_status,msu_account_name,msp_status,msp_account_name)" +
            " VALUES (#{number},#{msuInstanceId},#{mspInstanceId},#{requesterName},#{requesterLocation},#{requesterEmail},#{requesterPhone}," +
            "#{serviceCatalog},#{category},#{impact},#{urgency},#{priority},#{requestType}," +
            "#{ciType},#{ci},#{requestDescription},#{createdAt},#{updatedAt}," +
            "#{createdBy},#{updatedBy},#{assignedUser},#{assignedGroup},#{responseTime},#{resolveTime}," +
            "#{closeTime},#{solution},#{closeCode},#{msuStatus},#{msuAccountName},#{mspStatus},#{mspAccountName});")
    void create(Incident incident);

    @Update("UPDATE msp_incidents SET " +
            "requester_location     = #{requesterLocation}," +
            "requester_name         = #{requesterName},"+
            "requester_email        = #{requesterEmail}," +
            "requester_phone        = #{requesterPhone},"+
            "request_type           = #{requestType},"+
            "service_catalog        = #{serviceCatalog}," +
            "category               = #{category}," +
            "impact                 = #{impact}," +
            "urgency                = #{urgency}," +
            "priority               = #{priority}," +
            "ci_type                = #{ciType}," +
            "ci                     = #{ci}," +
            "updated_at             = #{updatedAt}," +
            "updated_by             = #{updatedBy}," +
            "assigned_user          = #{assignedUser}," +
            "assigned_group         = #{assignedGroup}," +
            "response_time          = #{responseTime},"+
            "resolve_time           = #{resolveTime},"+
            "close_time             = #{closeTime},"+
            "solution               = #{solution},"+
            "msu_status             = #{msuStatus},"+
            "close_code             = #{closeCode}, "+
            "msp_account_name       = #{mspAccountName}, "+
            "msp_instance_id        = #{mspInstanceId}, "+
            "msp_status             = #{mspStatus} "+
            "WHERE msp_instance_id  = #{mspInstanceId};")
    void update(Incident incident);

    @Select("SELECT count(0) FROM msp_incidents WHERE msu_account_name = #{msuAccountName} and msu_instance_id = #{msuInstanceId}")
    long countByMsuAccountNameAndInstanceId(@Param("msuAccountName") String msuAccountName,
                                            @Param("msuInstanceId") String msuInstanceId);

    @Select("SELECT msp_instance_id FROM msp_incidents WHERE msu_account_name = #{msuAccountName} and msu_instance_id = #{msuInstanceId}")
    String findMspInstanceIdByMsuAccountNameAndInstanceId(@Param("msuAccountName") String msuAccountName,
                                            @Param("msuInstanceId") String msuInstanceId);

    @Update("UPDATE msp_incidents SET " +
            "requester_location     = #{requesterLocation}," +
            "requester_name         = #{requesterName},"+
            "requester_email        = #{requesterEmail}," +
            "requester_phone        = #{requesterPhone},"+
            "request_type           = #{requestType},"+
            "service_catalog        = #{serviceCatalog}," +
            "category               = #{category}," +
            "impact                 = #{impact}," +
            "urgency                = #{urgency}," +
            "priority               = #{priority}," +
            "ci_type                = #{ciType}," +
            "ci                     = #{ci}," +
            "updated_at             = #{updatedAt}," +
            "updated_by             = #{updatedBy}," +
            "msu_status             = #{msuStatus} "+
            "WHERE msu_account_name = #{msuAccountName} AND msu_instance_id = #{msuInstanceId};")
    void updateMsuStatusByMsuAccountAndMsuInstanceId(Incident incident);

    @Update("UPDATE msp_incidents SET " +
            "requester_location     = #{requesterLocation}," +
            "requester_name         = #{requesterName},"+
            "requester_email        = #{requesterEmail}," +
            "requester_phone        = #{requesterPhone},"+
            "request_type           = #{requestType},"+
            "service_catalog        = #{serviceCatalog}," +
            "category               = #{category}," +
            "impact                 = #{impact}," +
            "urgency                = #{urgency}," +
            "priority               = #{priority}," +
            "ci_type                = #{ciType}," +
            "ci                     = #{ci}," +
            "updated_at             = #{updatedAt}," +
            "updated_by             = #{updatedBy}," +
            "created_at             = #{createdAt}," +
            "created_by             = #{createdBy}," +
            "solution               = #{solution},"+
            "close_code             = #{closeCode}, "+
            "assigned_user          = #{assignedUser}," +
            "assigned_group         = #{assignedGroup}," +
            "response_time          = #{responseTime},"+
            "resolve_time           = #{resolveTime},"+
            "close_time             = #{closeTime},"+
            "msu_status             = #{msuStatus}, "+
            "msp_account_name       = #{mspAccountName}, "+
            "msp_instance_id        = #{mspInstanceId}, "+
            "msp_status             = #{mspStatus} "+
            "WHERE msu_account_name = #{msuAccountName} AND msu_instance_id = #{msuInstanceId};")
    void updateMspStatusByMsuAccountAndMsuInstanceId(Incident incident);

    @Select("SELECT count(0) FROM msp_incidents WHERE msp_instance_id is null")
    long countMonitoredByKey(@Param("keyword")String keyword);

    List<Incident> findAllMonitoredByKey(@Param("keyword")String keyword,@Param("pageable")Pageable pageable);

    @Select("SELECT * FROM msp_incidents WHERE id = #{id}")
    Incident findById(@Param("id") String id);
}
