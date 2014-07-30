/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.PageRequest;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.service.MutableUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <h1>具备一定权限的管理者对用户的管理</h1>
 * <p/>
 * 因为站在管理者的角度看来，有许多个用户，所以该控制器取名为复数
 * <p/>
 * <br/>用户注册等业务功能留待以后进行业务分析之后再实际开发
 */
@RestController
@RequestMapping("/admin/api/users")
public class UsersController extends ApplicationController {
    @Autowired
    MutableUserService userService;

    /**
     * <h2>查询用户列表</h2>
     * <p/>
     * GET /admin/api/users?keyword=theKeyWord&page=1
     *
     * @param keyword 用户特征词，可能没有
     * @param page    第几页
     * @param size    分页参数
     *                即便这个值被放到用户profile,或者session里面
     *                那也是前端程序读取到这个值，而后传递过来，而不是这里去读取
     * @return 查询结果
     * 提供给前端的分页信息放在response头中
     */
    @RequestMapping
    public List<User> all(
            HttpServletResponse response,
            @RequestParam(required = false, value="keyword") String keyword,
            @RequestParam(required = false, value="page", defaultValue = "0") int page,
            @RequestParam(required = false, value="size", defaultValue = "40") int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<User> thePage = userService.findAll(keyword, pageable);
        //把分页的整体信息放在http头中
        renderHeader(response, thePage);
        //把数据放在http的body中
        return thePage.getContent();
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
    public User create(User user) {
        logger.info("Creating {}", user.getUsername());
        User created = userService.createUser(user);
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
                       @RequestBody User user) {
        logger.info("Updating {}", username);
        User exist = userService.findUser(username);
        exist.apply(user);
        userService.updateUser(exist);
        User updated = userService.findUser(exist.getUsername());
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
