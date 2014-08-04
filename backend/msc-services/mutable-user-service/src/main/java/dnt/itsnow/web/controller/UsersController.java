/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.User;
import dnt.itsnow.service.MutableUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>具备一定权限的管理者对用户的管理</h1>
 * <p/>
 * 因为站在管理者的角度看来，有许多个用户，所以该控制器取名为复数
 * <p/>
 * <p>用户注册等业务功能留待以后进行业务分析之后再实际开发</p>
 * <p>虽然mutable-xx-services仅仅会部署在msc实体中，但为了统一的权限规划，其相应的SPI均以admin开头</p>
 */
@RestController
@RequestMapping("/admin/api/users")
public class UsersController extends SessionSupportController<User> {
    @Autowired
    MutableUserService userService;

    /**
     * <h2>查询用户列表</h2>
     * <p/>
     * GET /admin/api/users?keyword=theKeyWord&page=1
     *
     * @param keyword 用户特征词，可能没有
     * @return 查询结果
     * 提供给前端的分页信息放在response头中
     */
    @RequestMapping
    public List<User> index( @RequestParam(required = false, value="keyword", defaultValue = "") String keyword ) {
        indexPage = userService.findAll(keyword, pageRequest);
        //把分页的整体信息放在http头中
        //把数据放在http的body中
        return indexPage.getContent();
    }


    /**
     * <h2>创建一个用户</h2>
     * <p/>
     * POST /admin/api/users
     *
     * @param user 需要创建的用户对象，通过HTTP BODY POST上来
     * @return 创建之后的用户信息，不包括密码等敏感信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public User create(@Valid User user) {
        logger.info("Creating {}", user.getUsername());
        User created = userService.create(user);
        cleanSensitive(created);
        logger.info("Created  {} with id {}", created.getUsername(), created.getId());
        return created;
    }

    /**
     * <h2>更新一个用户的基本信息</h2>
     * <p/>
     * PUT /admin/api/users/#{username}
     *
     * @param username 需要更新的用户原始用户名
     * @param user     需要更新的用户对象，通过HTTP BODY POST上来
     * @return 更新之后的用户信息，不包括密码等敏感信息
     */
    @RequestMapping(value = "{username}", method = RequestMethod.PUT)
    public User update(@PathVariable("username") String username,
                       @RequestBody @Valid  User user) {
        logger.info("Updating {}", username);
        User exist = userService.findByUsername(username);
        exist.apply(user);
        userService.update(exist);
        User updated = userService.findByUsername(exist.getUsername());
        cleanSensitive(updated);
        if (updated.getUsername().equalsIgnoreCase(username)) {
            logger.info("Updated  {}", username);
        } else {
            logger.info("Updated  {} -> {}", username, updated.getUsername());
        }
        return updated;
    }


    /**
     * <h2>管理员更新一个用户的密码</h2>
     * <p/>
     * PUT /admin/api/users/#{username}/reset_password
     *
     * @param username    需要更新的用户原始用户名
     * @param newPassword 需要更新的用户密码，通过HTTP BODY POST上来
     */
    @RequestMapping(value = "{username}/reset_password", method = RequestMethod.PUT)
    public void resetPassword(@PathVariable("username") String username,
                              @RequestBody String newPassword) {
        logger.info("Reset password for {}", username);
        userService.changePassword(username, newPassword);
        logger.info("Reset password for {} (done!)", username);

    }

    private void cleanSensitive(User user) {
        user.setPassword(null);
    }


}