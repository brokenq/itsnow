/**
 * @author XiongJie, Date: 14-7-29
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.service.MutableUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    MutableUserService userService;

    /**
     * <h2>修改当前的用户个人信息</h2>
     *
     * PUT /api/profile
     *
     * @return 当前用户对象，通过HTTP BODY提交
     */
    @RequestMapping(method = RequestMethod.PUT)
    public User update(HttpServletRequest request,
                       @RequestBody User user){
        String username = request.getRemoteUser();
        logger.info("Updating {}", username);
        User exist = userService.findUser(username);
        exist.apply(user);
        userService.updateUser(exist);
        User updated = userService.findUser(exist.getUsername());
        updated.setPassword(null);
        if( updated.getUsername().equalsIgnoreCase(username)){
            logger.info("Updated  {}", username);
        }else{
            logger.info("Updated  {} -> {}", username, updated.getUsername());
        }
        return updated;
    }
}
