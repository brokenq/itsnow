/**
 * @author XiongJie, Date: 14-7-29
 */
package dnt.itsnow.web.controller;

import net.happyonroad.platform.web.controller.ApplicationController;
import net.happyonroad.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MutableUserService;
import dnt.itsnow.web.model.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <h1>用户个人的密码</h1>
 * 因为站在特定用户的角度看来，他只有一个“当前密码”，所以该控制器命名为单数
 */
@RestController
@RequestMapping("/api/password")
public class PasswordController extends ApplicationController {
    @Autowired
    MutableUserService userService;

    /**
     * <h2>某个用户提交密码忘记的请求</h2>
     *
     * POST /api/password/forget
     *
     * @param forgetRequest  重置密码的请求作为http body提交
     */
    @RequestMapping(value = "forget", method = RequestMethod.POST)
    public void forgetPassword(HttpServletRequest request,
                               @RequestBody @Valid ChangePasswordRequest forgetRequest){

    }

    /**
     * <h2>某个用户修改自己的密码</h2>
     *
     * PUT /api/password/change
     *
     * @param changeRequest  修改密码的请求作为http body提交
     */
    @RequestMapping(value = "change", method = RequestMethod.PUT)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest changeRequest) {
    //    Principal principal = request.getUserPrincipal(); // 等价方法： request.getRemoteUser()
    //    String username = principal.getName();
        String username=changeRequest.getUsername();
        logger.info("Changing password for {}", username);

/*
        if(StringUtils.isBlank(newPassword))
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The new password can't be null");
        if(!StringUtils.equals(newPassword, repeat))
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The new password is not consistent");
*/
        //与上述代码基本等价，只是异常不一样，实现机制不太一样
        //validatorFactory.getValidator().validate(changeRequest);
        // 这些逻辑原本应该放在 User 模型上，或者change password模型上
        // 这一段应该是服务的功能
        if(!userService.challenge(username, changeRequest.getOldPassword()))
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The old password is not valid!");

        userService.changePassword(username, changeRequest.getPassword());
        logger.info("Changed  password for {}", username);
    }

}
