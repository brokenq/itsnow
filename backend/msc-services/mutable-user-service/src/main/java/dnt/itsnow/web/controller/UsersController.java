/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MutableUserService;
import dnt.itsnow.web.model.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 对用户的管理
 */
@RestController("/admin/api/users")
public class UsersController extends ApplicationController{
    @Autowired
    MutableUserService userService;

    /**
     * <h2>创建一个用户</h2>
     *
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
     *
     * PUT /admin/api/users/#{username}
     *
     * @param username 需要更新的用户原始用户名
     * @param user 需要更新的用户对象，通过HTTP BODY POST上来
     * @return 更新之后的用户信息，不包括密码等敏感信息
     */
    @RequestMapping(value = "#{username}", method = RequestMethod.PUT)
    public User update( @PathVariable("username") String username,
                        @RequestBody User user){
        logger.info("Updating {}", username);
        User exist = userService.findUser(username);
        exist.apply(user);
        userService.updateUser(exist);
        User updated = userService.findUser(exist.getUsername());
        cleanSensitive(updated);
        if( updated.getUsername().equalsIgnoreCase(username)){
            logger.info("Updated  {}", username);
        }else{
            logger.info("Updated  {} -> {}", username, updated.getUsername());
        }
        return updated;
    }

    /**
     * <h2>某个用户修改自己的密码</h2>
     *
     * PUT /change_password
     *
     * @param changeRequest  修改密码的请求作为http body提交
     */
    @RequestMapping(value = "/change_password", method =  RequestMethod.PUT)
    public void changePassword(HttpServletRequest request,
                               @RequestBody ChangePasswordRequest changeRequest){
        Principal principal = request.getUserPrincipal(); // 等价方法： request.getRemoteUser()
        String username = principal.getName();
        logger.info("Changing password for {}", username);

/*
        if(StringUtils.isBlank(newPassword))
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The new password can't be null");
        if(!StringUtils.equals(newPassword, repeat))
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The new password is not consistent");
*/
        //与上述代码基本等价，只是异常不一样，实现机制不太一样
        validatorFactory.getValidator().validate(changeRequest);
        // 这些逻辑原本应该放在 User 模型上，或者change password模型上
        // 这一段应该是服务的功能
        if(!userService.challenge(username, changeRequest.getOldPassword()))
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The old password is not valid!");

        userService.changePassword(username, changeRequest.getNewPassword());
        logger.info("Changed  password for {}", username);
    }

    private void cleanSensitive(User user) {
        user.setPassword(null);
    }


}
