package dnt.itsnow.service;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.Group;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;

import java.util.List;

/**
 * <h1>MSC组管理持久层</h1>
 */
public interface MscGroupService {

    /**
     * <h2>可根据关键字查询组信息</h2>
     * <p></p>
     *
     * @param keyword     关键字
     * @param pageRequest 分页实体类
     * @return 组信息分页列表
     */
    public Page<Group> findAll(String keyword, PageRequest pageRequest);

    /**
     * <h2>根据关键字查询组相关的信息</h2>
     * <p></p>
     *
     * @param name        组名
     * @param pageRequest 分页实体类
     * @return 组相关信息分页列表
     */
    public Page<Group> findAllRelevantInfo(String name, PageRequest pageRequest);

    /**
     * <h2>根据SN查询组信息</h2>
     * <p></p>
     *
     * @param name 组序列号
     * @return
     */
    public Group findByName(String name);

    /**
     * <h2>新建组</h2>
     * <p></p>
     *
     * @param group 待建组
     * @return 已新建组
     * @throws dnt.itsnow.exception.GroupException
     */
    public Group create(Group group) throws GroupException;

    /**
     * <h2>更新组信息</h2>
     * <p></p>
     *
     * @param group 组实体类
     * @return 已更新的组
     * @throws dnt.itsnow.exception.GroupException
     */
    public Group update(Group group) throws GroupException;

    /**
     * <h2>销毁组</h2>
     * <p></p>
     *
     * @param group 组实体类
     * @return
     * @throws dnt.itsnow.exception.GroupException
     */
    public void destroy(Group group) throws GroupException;

    List<Group> search(String keyword);

}
