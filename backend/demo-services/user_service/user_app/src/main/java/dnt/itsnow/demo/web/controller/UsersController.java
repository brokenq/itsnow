/**
 * Developer: Kadvin Date: 14-7-14 下午5:08
 */
package dnt.itsnow.demo.web.controller;

import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.PageRequest;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.demo.api.UserService;
import dnt.itsnow.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The user controller
 */
@RestController
@RequestMapping("/demo/users")
public class UsersController extends ApplicationController {

    @Autowired
    UserService userService;

    /**
     * <h2>查询用例列表</h2>
     *
     * GET /api/users?keyword=theKeyWord&page=1
     *
     * @param keyword 用户特征词，可能没有
     * @param page 第几页
     * @param size 分页参数
     *             即便这个值被放到用户profile,或者session里面
     *             那也是前端程序读取到这个值，而后传递过来，而不是这里去读取
     * @return 查询结果
     */
    @RequestMapping
    public List<User> all(@RequestParam(required = false, defaultValue = "") String keyword,
                          @RequestParam(required = false, defaultValue = "0") int page,
                          @RequestParam(required = false, defaultValue = "40") int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<User> thePage = userService.findAll(keyword, pageable);
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
    @RequestMapping("${username}")
    public User find(@PathVariable("username") String username ){
        return userService.find(username);
    }
}
