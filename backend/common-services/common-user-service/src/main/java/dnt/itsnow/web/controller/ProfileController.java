/**
 * @author XiongJie, Date: 14-7-29
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>用户对自身的管理</h1>
 *
 * 因为站在特定用户的角度看来，只有一个“自身”，所以该控制器命名为单数
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController extends ApplicationController {
    @Autowired
    UserService userService;

    /**
     * <h2>查看当前的用户个人信息</h2>
     *
     * GET /api/profile
     *
     * @return 当前用户对象
     */
    @RequestMapping
    public User show(HttpServletRequest request) {
        String username = request.getRemoteUser();
        logger.info("Viewing profile of {}", username);
        User user = userService.loadUserByUsername(username);
        logger.info("Viewed  profile of {}", username);
        return user;
    }

}
