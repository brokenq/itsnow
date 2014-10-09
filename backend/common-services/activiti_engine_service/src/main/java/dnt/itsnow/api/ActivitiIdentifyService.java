package dnt.itsnow.api;

import dnt.itsnow.model.User;
import dnt.itsnow.model.Group;
import java.util.List;

/**
 * <h2>Activiti Identify Service</h2>
 */
public interface ActivitiIdentifyService {

    /**
     * 添加工作流用户以及用户组
     * @param user      用户对象{@link User}
     * @param groupIds   用户拥有的角色ID集合
     */
    void newActivitiUser(User user, List<Long> groupIds);

    /**
     * 更新工作流用户以及用户组
     * @param user          用户对象{@link User}
     * @param groupIds       用户拥有的用户组ID集合
     * @param activitiUser  Activiti引擎的用户对象，{@link org.activiti.engine.identity.User}
     */
    void updateActivitiUser(User user, List<Long> groupIds, org.activiti.engine.identity.User activitiUser);

    /**
     * 删除工作流的用户以及用户组关系
     * @param user      用户对象{@link User}
     */
    void deleteActivitiUser(User user);


    /**
     * 添加工作流用户组
     * @param group      用户组对象{@link Group}
     */
    void newActivitiGroup(Group group);

    /**
     * 更新工作流用户组
     * @param group          用户对象{@link Group}
     */
    void updateActivitiGroup(Group group);

    /**
     * 删除工作流的用户组
     * @param group      用户对象{@link Group}
     */
    void deleteActivitiGroup(Group group);
}
