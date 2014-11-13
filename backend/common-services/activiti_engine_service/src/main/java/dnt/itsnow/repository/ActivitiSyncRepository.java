package dnt.itsnow.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

/**
 * <h2>Activity Sync Repository</h2>
 */
public interface ActivitiSyncRepository {

    @Insert("INSERT INTO ACT_ID_GROUP(ID_,NAME_,TYPE_) SELECT name,name,'group' FROM itsnow_msc.groups WHERE name NOT IN (SELECT ID_ FROM ACT_ID_GROUP)")
    int insertGroups();

    @Delete("DELETE FROM ACT_ID_GROUP WHERE ID_ NOT IN (select name FROM itsnow_msc.groups)")
    int deleteGroups();

    @Insert("INSERT INTO ACT_ID_USER(ID_,EMAIL_) SELECT username,email FROM itsnow_msc.users WHERE username not in (select ID_ FROM ACT_ID_USER)")
    int insertUsers();

    @Delete("DELETE FROM ACT_ID_USER WHERE ID_ NOT IN (select username FROM itsnow_msc.users)")
    int deleteUsers();

    @Insert("INSERT INTO ACT_ID_MEMBERSHIP(USER_ID_,GROUP_ID_) SELECT username,groupname FROM itsnow_msc.group_members WHERE CONCAT(username,groupname) NOT IN (select CONCAT(USER_ID_,GROUP_ID_) FROM ACT_ID_MEMBERSHIP)")
    int insertGroupMembers();

    @Delete("DELETE FROM ACT_ID_MEMBERSHIP WHERE CONCAT(USER_ID_,GROUP_ID_) NOT IN (select CONCAT(username,groupname) FROM itsnow_msc.group_members)")
    int deleteGroupMembers();
}
