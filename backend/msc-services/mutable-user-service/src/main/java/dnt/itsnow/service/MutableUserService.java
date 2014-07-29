/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.service;

import dnt.itsnow.model.User;

/** 可以进行修改的用户服务 */
public interface MutableUserService extends UserService {

    /**
     * 创建用户
     *
     * @param user 待创建的用户，其密码为明文，需要进行加密
     */
    User createUser(User user);

    /**
     * 更新用户
     *
     * @param user 待更新的用户，忽略其密码，密码的修改应该走另外的接口
     */
    void updateUser(User user);

    /**
     * 修改特定用户的密码
     *
     * @param username    待修改密码的用户名称
     * @param newPassword 新的密码，明文
     */
    void changePassword(String username, String newPassword);
}
