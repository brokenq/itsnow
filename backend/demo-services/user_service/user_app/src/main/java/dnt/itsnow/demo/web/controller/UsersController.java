/**
 * Developer: Kadvin Date: 14-7-14 下午5:08
 */
package dnt.itsnow.demo.web.controller;

import dnt.itsnow.demo.api.UserService;
import dnt.itsnow.demo.model.User;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.web.controller.ApplicationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>The users controller</h1>
 */
@RestController
@RequestMapping("/demo/users")
public class UsersController extends ApplicationController {

    @Autowired
    UserService userService;

    /**
     * <h2>查询用户列表</h2>
     *
     * GET /api/users?keyword=theKeyWord&page=1
     *
     * @param keyword 用户特征词，可能没有
     * @return 查询结果
     */
    @RequestMapping
    public List<User> index(@RequestParam(required = false, defaultValue = "") String keyword) {
        logger.debug("Listing users by {}", keyword);
        Page<User> thePage = userService.findAll(keyword, pageRequest);
        logger.debug("Found   users {}/{}", thePage.getNumberOfElements(), thePage.getTotalElements());
        return thePage.getContent();
    }

    /**
     * <h2>查看特定用户信息</h2>
     *
     * GET /api/users/admin
     *
     * @param username 用户名称
     * @return 用户信息
     */
    @RequestMapping("{username}")
    public User show(@PathVariable("username") String username ){
        logger.debug("Viewing {}", username);
        User user = userService.find(username);
        logger.debug("Viewed  {}", user);
        return user;
    }
}
