package dnt.itsnow.repository;

import dnt.itsnow.model.Incident;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by jacky on 2014/7/28.
 */
public interface MspIncidentRepository {

    @Select("select * from msp_incidents where msp_instance_id = #{id}")
    Incident findByInstanceId(@Param("id") String id);

    List<Incident> findAllByInstanceIds(@Param("ids")List<String> ids,@Param("keyword")String keyword,@Param("pageable")Pageable pageable);

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("INSERT INTO `msp_incidents` (`number`,`msu_instance_id`,`msp_instance_id`,`requester_name`,`requester_location`,`requester_email`,`requester_phone`," +
            "`service_catalog`,`category`,`impact`,`urgency`,`priority`,`request_type`," +
            "`ci_type`,`ci`,`request_description`,`created_at`,`updated_at`," +
            "`created_by`,`updated_by`,`assigned_user`,`assigned_group`,`response_time`,`resolve_time`," +
            "`close_time`,`solution`,`close_code`,`msu_status`,`msu_account_name`,`msp_status`,`msp_account_name`)" +
            " VALUES (#{number},#{msuInstanceId},#{mspInstanceId},#{requesterName},#{requesterLocation},#{requesterEmail},#{requesterPhone}," +
            "#{serviceCatalog},#{category},#{impact},#{urgency},#{priority},#{requestType}," +
            "#{ciType},#{ci},#{requestDescription},#{createdAt},#{updatedAt}," +
            "#{createdBy},#{updatedBy},#{assignedUser},#{assignedGroup},#{responseTime},#{resolveTime}," +
            "#{closeTime},#{solution},#{closeCode},#{msuStatus},#{msuAccountName},#{mspStatus},#{mspAccountName});")
    void create(Incident incident);

    @Update("UPDATE msp_incidents SET " +
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
            "`updated_at` = #{updatedAt}," +
            "`updated_by` = #{updatedBy}," +
            "`assigned_user` = #{assignedUser}," +
            "`assigned_group` = #{assignedGroup}," +
            "`response_time` = #{responseTime},"+
            "`resolve_time` = #{resolveTime},"+
            "`close_time` = #{closeTime},"+
            "`solution` = #{solution},"+
            "`msu_status` = #{msuStatus},"+
            "`close_code` = #{closeCode}, "+
            "`msp_account_name` = #{mspAccountName}, "+
            "`msp_instance_id` = #{mspInstanceId}, "+
            "`msp_status` = #{mspStatus} "+
            " WHERE `msp_instance_id` = #{mspInstanceId};")
    void update(Incident incident);

    @Update("UPDATE `msp_incidents` SET "+
            "msp_status = #{mspStatus} "+
            "WHERE msp_instance_id = #{mspInstanceId};")
    void updateStatus(@Param("mspInstanceId") String mspInstanceId,@Param("mspStatus") String mspStatus);

    @Update("UPDATE `msp_incidents` SET " +
            "response_time = CURRENT_TIMESTAMP " +
            "WHERE msp_instance_id = #{mspInstanceId};")
    void updateResponseTime(@Param("mspInstanceId") String mspInstanceId);

    @Update("UPDATE `msp_incidents` SET " +
            "resolve_time = CURRENT_TIMESTAMP " +
            "WHERE msp_instance_id = #{mspInstanceId};")
    void updateResolveTime(@Param("mspInstanceId") String mspInstanceId);

    @Update("UPDATE `msp_incidents` SET " +
            "close_time = CURRENT_TIMESTAMP " +
            "WHERE msp_instance_id = #{mspInstanceId};")
    void updateCloseTime(@Param("mspInstanceId") String mspInstanceId);

    @Update("UPDATE `msp_incidents` SET " +
            "close_time = CURRENT_TIMESTAMP," +
            "close_code = #{closeCode} " +
            "WHERE id = #{id}")
    void close(Incident incident);

    @Select("select count(0) from msp_incidents where msu_account_name = #{msuAccountName} and msu_instance_id = #{msuInstanceId}")
    long countByMsuAccountNameAndInstanceId(@Param("msuAccountName") String msuAccountName,
                                            @Param("msuInstanceId") String msuInstanceId);

    @Update("UPDATE msp_incidents SET " +
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
            "`updated_at` = #{updatedAt}," +
            "`assigned_user` = #{assignedUser}," +
            "`assigned_group` = #{assignedGroup}," +
            "`solution` = #{solution},"+
            "`close_code` = #{closeCode}, "+
            "`msu_status` = #{msuStatus}, "+
            "`msp_status` = #{mspStatus} "+
            " WHERE `msu_account_name` = #{msuAccountName} and `msu_instance_id` = #{msuInstanceId};")
    void updateByMsuAccountAndMsuInstanceId(Incident incident);

}
