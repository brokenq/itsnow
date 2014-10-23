/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.service;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>可以进行修改的用户服务 </h1>
 * <p/>
 * 继承了UserService，是为了能够兼容
 */
public interface MutableUserService extends CommonUserService {

    /**
     * <h2>根据关键词查询用户</h2>
     *
     * @param keyword  关键词
     * @param pageable 分页需求
     * @return 组织在分页容器中的用户数据
     */
    Page<User> findAll(String keyword, Pageable pageable);

    /**
     * 创建用户
     *
     * @param user 待创建的用户，其密码为明文，需要进行加密
     */
    User create(User user);

    /**
     * 更新用户
     *
     * @param user 待更新的用户，忽略其密码，密码的修改应该走另外的接口
     */
    void update(User user);

    /**
     * 修改特定用户的密码
     *
     * @param username    待修改密码的用户名称
     * @param newPassword 新的密码，明文
     */
    void changePassword(String username, String newPassword);

    /**
     * 删除特定帐户下所有的用户
     * @param accountId 被删除的帐户ID
     */
    void deleteByAccountId(Long accountId);
}
