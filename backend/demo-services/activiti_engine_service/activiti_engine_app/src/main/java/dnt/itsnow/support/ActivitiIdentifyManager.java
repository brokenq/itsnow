package dnt.itsnow.support;

import dnt.itsnow.api.ActivitiIdentifyService;
import dnt.itsnow.model.Group;
import dnt.itsnow.model.User;
import dnt.spring.Bean;
import dnt.util.StringUtils;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.UserQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by jacky on 2014/7/30.
 */
public class ActivitiIdentifyManager extends Bean implements ActivitiIdentifyService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessEngine processEngine;

    @Override
    public void newActivitiUser(User user, List<Long> groupIds) {
        if(user == null)
            log.warn("user is null!");
        else{
            UserQuery userQuery = processEngine.getIdentityService().createUserQuery();
            List<org.activiti.engine.identity.User> activitiUsers = userQuery.userId(user.getId().toString()).list();

            if(activitiUsers.size() == 1) {//已存在，更新
                updateActivitiUser(user, groupIds, activitiUsers.get(0));
            }else if(activitiUsers.size() > 1) {//多个
                String errorMsg = "发现重复用户：id="+ user.getId();
                log.warn(errorMsg);
            }else{//不存在，新增用户和关系
                // 添加用户
                saveActivitiUser(user);
                // 添加membership
                addMembershipToIdentify(groupIds, user.getId().toString());
            }
        }
    }

    @Override
    public void updateActivitiUser(User user, List<Long> groupIds, org.activiti.engine.identity.User activitiUser) {
        String userId = user.getId().toString();

        // 更新用户主体信息
        cloneAndSaveActivitiUser(user, activitiUser);

        // 删除用户的membGership
        List<org.activiti.engine.identity.Group> activitiGroups = processEngine.getIdentityService().createGroupQuery().groupMember(userId).list();
        for(org.activiti.engine.identity.Group group : activitiGroups) {
            processEngine.getIdentityService().deleteMembership(userId, group.getId());
            log.info("delete user {} from group {}", userId,group.getId());
        }

        // 添加membership
        addMembershipToIdentify(groupIds, userId);
    }

    @Override
    public void deleteActivitiUser(User user) {
        String userId = user.getId().toString();

        // 删除用户的membGership
        List<org.activiti.engine.identity.Group> activitiGroups = processEngine.getIdentityService().createGroupQuery().groupMember(userId).list();
        for(org.activiti.engine.identity.Group group : activitiGroups) {
            processEngine.getIdentityService().deleteMembership(userId, group.getId());
            log.info("delete user {} from group {}", userId,group.getId());
        }

        // 删除Activiti User
        processEngine.getIdentityService().deleteUser(userId);
    }

    @Override
    public void newActivitiGroup(Group group) {
        String groupId = group.getId().toString();

        GroupQuery groupQuery = processEngine.getIdentityService().createGroupQuery();
        List<org.activiti.engine.identity.Group> activitiGroups = groupQuery.groupId(groupId).list();

        if(activitiGroups.size()==1){//已存在，更新
            updateActivitiGroup(group);
        }else if(activitiGroups.size()>1){//重复
            log.warn("存在重复的用户组 {}",group.getGroupName());
        }else{//不存在，新增
            saveActivitiGroup(group);
        }
    }

    @Override
    public void updateActivitiGroup(Group group) {
        String groupId = group.getId().toString();

        GroupQuery groupQuery = processEngine.getIdentityService().createGroupQuery();
        org.activiti.engine.identity.Group activitiGroup = groupQuery.groupId(groupId).singleResult();
        activitiGroup.setName(group.getGroupName());
        processEngine.getIdentityService().saveGroup(activitiGroup);

    }

    @Override
    public void deleteActivitiGroup(Group group) {
        processEngine.getIdentityService().deleteGroup(group.getId().toString());
        log.info("delete activiti group {}",group.getGroupName());
    }

    /**
     * 添加一个用户到Activiti {@link User}
     * @param user  用户对象, {@link User}
     */
    private void saveActivitiUser(User user) {
        String userId = user.getId().toString();
        org.activiti.engine.identity.User activitiUser = processEngine.getIdentityService().newUser(userId);
        cloneAndSaveActivitiUser(user, activitiUser);
        log.info("add activiti user: {}", activitiUser.getFirstName());
    }

    /**
     * 添加Activiti Identify的用户于组关系
     * @param groupIds   用户组ID集合
     * @param userId    用户ID
     */
    private void addMembershipToIdentify(List<Long> groupIds, String userId) {
        for(Long groupId : groupIds) {
            processEngine.getIdentityService().createMembership(userId, groupId.toString());
            log.info("add activiti user {} to group {}",userId, groupId);
        }
    }

    /**
     * 使用系统用户对象属性设置到Activiti User对象中
     * @param user          系统用户对象
     * @param activitiUser  Activiti User
     */
    private void cloneAndSaveActivitiUser(User user, org.activiti.engine.identity.User activitiUser) {
        activitiUser.setFirstName(user.getUsername());
        activitiUser.setLastName(StringUtils.EMPTY);
        activitiUser.setPassword(StringUtils.EMPTY);
        activitiUser.setEmail(user.getEmail());
        processEngine.getIdentityService().saveUser(activitiUser);
    }

    /**
     * 添加一个用户组到Activiti {@link Group}
     * @param group  用户组对象, {@link Group}
     */
    private void saveActivitiGroup(Group group) {
        String groupId = group.getId().toString();
        org.activiti.engine.identity.Group activitiGroup = processEngine.getIdentityService().newGroup(groupId);
        activitiGroup.setName(group.getGroupName());
        processEngine.getIdentityService().saveGroup(activitiGroup);
        log.info("add activiti group: {}", group.getGroupName());
    }
}
