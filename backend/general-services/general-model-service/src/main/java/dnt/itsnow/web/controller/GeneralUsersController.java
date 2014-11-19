package dnt.itsnow.web.controller;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.service.GeneralUserService;
import dnt.itsnow.web.model.ChangePasswordRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import javax.validation.Valid;
/**
 * <h1>MSP/MSU用户控制器</h1>
 * <pre>
 * <b>HTTP   URI                           方法      含义  </b>
 *  GET      /api/users              index     列出该账户下所有的用户，并且分页显示
 *  GET      /api/users /{username}  show      列出特定的用户记录
 *  POST     /api/users              create    创建一个用户
 *  PUT      /api/users/{username}   update    修改一个指定的用户
 *  DELETE   /api/users/{username}   delete    删除指定的用户记录
 * </pre>
 */
@RestController
@RequestMapping("/api/users")
public class GeneralUsersController extends SessionSupportController <User>{
    @Autowired
    GeneralUserService userService;
    private User user;
    /**
     * <h2>查询用户列表</h2>
     * <p/>
     * GET /admin/api/users?keyword=theKeyWord&page={int}&count={int}
     * @param keyword 用户特征词，可能没有
     * @return 查询结果
     * 提供给前端的分页信息放在response头中
     */
    @RequestMapping
    public Page<User> index( @RequestParam(required = false, value="keyword", defaultValue = "") String keyword ) {
        logger.debug("Listing Users by keyword: {}", keyword);
        indexPage = userService.findAll(keyword, pageRequest,mainAccount);
        logger.debug("Listed  {}", indexPage);
        return indexPage;
    }
    /**
     * <h2>查看一个用户</h2>
     * <p/>
     * GET /admin/api/users/{username}
     * @param username 用户名称
     * @return 用户实体类
     */
    @RequestMapping(value="{username}", method = RequestMethod.GET)
    public User show(@PathVariable("username") String username) {

        logger.debug("find user by {}", username);
        cleanSensitive(this.user);
        return this.user;
    }

    /**
     * <h2>创建一个用户</h2>
     * <p/>
     * POST /admin/api/users
     * @param user 需要创建的用户对象，通过HTTP BODY POST上来
     * @return 创建之后的用户信息，不包括密码等敏感信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public User create(@Valid  @RequestBody User user) {
        logger.info("Creating {}", user);
        logger.info("mainAccount{}",mainAccount.getId());
        user.setAccountId(mainAccount.getId());
        user.setAccount(mainAccount);
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
    public void destroy(@PathVariable("username") String username){
        logger.warn("Deleting {}");
        try {
            userService.delete(this.user);
        } catch (Exception e) {
            // 把service side异常转换为client side exception
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        logger.warn("Deleted  {}");
    }

    @RequestMapping(value = "check/{username}", method = RequestMethod.GET)
    public HashMap checkUnique(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        if( user != null ){
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate role name: " + user.getUsername());
        }else{
            return new HashMap();
        }
    }
    @RequestMapping(value = "checkEmail/{email:.+}", method = RequestMethod.GET)
    public HashMap checkUniqueEmail(@PathVariable("email") String email){
        User user = userService.findByEmail(email);
        if( user != null ){
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate role name: " + user.getEmail());
        }else{
            return new HashMap();
        }
    }
    @RequestMapping(value = "getUsersByAccount", method = RequestMethod.GET)
    public List<User> listUsers() {

        logger.info("Listing users by current account:{}", mainAccount);

        List<User> users =userService.findUsersByAccount(mainAccount);

        logger.info("Listed  {}", users);

        return users;
    }
    @RequestMapping(value = "change/password", method = RequestMethod.PUT)
    public void changePassword(@RequestBody @Valid ChangePasswordRequest changeRequest) {
         logger.info("change password:{}");
        userService.changePassword(changeRequest);
    }
    @BeforeFilter({"show", "update", "destroy"})
    public void initCurrentUser(@PathVariable("username") String username) {
        this.user=userService.findByUsername(username);

    }
    private void cleanSensitive(User user) {
        user.setPassword(null);
    }
}
