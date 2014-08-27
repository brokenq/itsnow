/**
 * @author XiongJie, Date: 14-7-29
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.service.CommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>用户对自身的管理</h1>
 *
 * <pre>
 * 一般而言，对于存储在 itsnow_msc 里面的模型的管理服务，只需要提供API（XxxService)
 * 不需要提供SPI(XxxController)，但如果被部署的实际子系统有直接使用(主要是展示)相应数据的需求
 * 那么，应该提供相应的控制器实现SPI，由于其相应的底层API没有对itsnow_msc的写能力（数据库用户，权限也应该限制）
 * 所以，这些控制器方法一般仅仅是无损操作（GET/HEAD/OPTION)方法
 * </pre>
 *
 * 因为站在特定用户的角度看来，只有一个“自身”，所以该控制器命名为单数
 *
 * TODO 添加测试用例
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController extends ApplicationController {
    @Autowired
    @Qualifier("plainUserService")
    CommonUserService userService;

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
