/**
 * @author XiongJie, Date: 14-7-28
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.User;
import net.happyonroad.platform.service.Page;
import dnt.itsnow.service.MutableUserService;
import net.happyonroad.platform.web.annotation.BeforeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;

import net.happyonroad.platform.web.exception.WebClientSideException;

import javax.validation.Valid;

/**
 * <h1>MSP/MSU对用户的管理</h1>
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
    private User user;

    /**
     * <h2>查询用户列表</h2>
     * <p/>
     * GET /admin/api/users?keyword=theKeyWord&page={int}&count={int}
     *
     * @param keyword 用户特征词，可能没有
     * @return 查询结果
     * 提供给前端的分页信息放在response头中
     */
    @RequestMapping
    public Page<User> index(@RequestParam(required = false, value = "keyword", defaultValue = "") String keyword) {
        logger.debug("Listing Users by keyword: {}", keyword);
        indexPage = userService.findAll(keyword, pageRequest);
        logger.debug("Listed  {}", indexPage);
        //把分页的整体信息放在http头中
        //把数据放在http的body中
        return indexPage;
    }

    /**
     * <h2>查看一个用户</h2>
     * <p/>
     * GET /admin/api/users/{username}
     *
     * @param username 用户名称
     * @return 用户实体类
     */
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public User show(@PathVariable("username") String username) {

        logger.debug("find user by {}", username);
        return this.user;
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
    public User create(@Valid @RequestBody User user) {
        logger.info("Creating {}", user);
        logger.info("userAccountId:{}", user.getAccountId());
        if (user.getAccountId() == null) {
            user.setAccountId(mainAccount.getId());
        }
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
                       @RequestBody User user) {
        logger.info("Updating {}", username);
        // User exist = userService.findByUsername(username);
        this.user.apply(user);
        userService.update(this.user);
        User updated = userService.findByUsername(this.user.getUsername());
        cleanSensitive(updated);
        if (updated.getUsername().equalsIgnoreCase(username)) {
            logger.info("Updated  {}", username);
        } else {
            logger.info("Updated  {} -> {}", username, updated.getUsername());
        }
        return updated;
    }

    @RequestMapping(value = "{username}", method = RequestMethod.DELETE)
    public void destroy(@PathVariable("username") String username) {
        logger.warn("Deleting {}");
        try {
            userService.delete(this.user);
        } catch (Exception e) {
            // 把service side异常转换为client side exception
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        logger.warn("Deleted  {}");
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

    @RequestMapping(value = "check/{username}", method = RequestMethod.GET)
    public HashMap checkUnique(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate role name: " + user.getUsername());
        } else {
            return new HashMap();
        }
    }

    @RequestMapping(value = "checkEmail/{email:.+}", method = RequestMethod.GET)
    public HashMap checkUniqueEmail(@PathVariable("email") String email) {
        logger.info("find byemail:{}", email);
        User user = userService.findByEmail(email);
        logger.info("return user{}", user);
        if (user != null) {
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate user emial: " + user.getEmail());
        } else {
            return new HashMap();
        }
    }

    @RequestMapping(value = "/{email:.+}/{username}/checkEmail", method = RequestMethod.GET)
    public HashMap checkUniqueUpdateEmail(@PathVariable("email") String email, @PathVariable("username") String username) {
        logger.info("find byemail:{}", email);
        User user = userService.findByEmail(email);
        logger.info("return user{}", user);
        if(user==null||user.getUsername().equals(username)){
            logger.info("into no confilct user{}", user);
            return new HashMap();
        }else{
            logger.info("in to conflict user {}", user);
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate user emil: " + user.getEmail());
        }
    }


    @RequestMapping(value = "/{password}/{username}/checkPwd", method = RequestMethod.GET)
    public HashMap checkPwd(@PathVariable("password") String password, @PathVariable("username") String username) {
        if (userService.challenge(username, password)) {
            return new HashMap();
        }else{

            throw new WebClientSideException(HttpStatus.CONFLICT, "bad user or password");
        }
    }


    @RequestMapping(value = "account/belongs", method = RequestMethod.GET)
    public List<User> listUsers() {

        logger.info("Listing users by current account:{}", mainAccount);

        List<User> users = userService.findUsersByAccount(mainAccount);

        logger.info("Listed  {}", users);

        return users;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initCurrentUser(@PathVariable("username") String username) {

        this.user = userService.findByUsername(username);
        this.cleanSensitive(this.user);
    }

    private void cleanSensitive(User user) {
        user.setPassword(null);
    }


}
