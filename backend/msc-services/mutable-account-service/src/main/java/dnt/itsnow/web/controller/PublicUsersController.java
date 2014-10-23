/**
 * Developer: Kadvin Date: 14-10-20 下午3:51
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.User;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.CommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <h1>MSC/MSU注册时用到的User服务</h1>
 * <pre>
 * <b>HTTP     URI                                         方法          含义  </b>
 * # POST      /public/accounts/users/:field/:value checkUnique  检测字段唯一性
 * </pre>
 */
@RestController
@RequestMapping("/public/users")
public class PublicUsersController extends ApplicationController<User> {
    @Autowired
    @Qualifier("plainUserService")
    CommonUserService userService;

    @RequestMapping("check/{field}/{value}")
    public HashMap checkUnique(@PathVariable("field") String field, @PathVariable("value") String value ){
        User found;
        if("username".equalsIgnoreCase(field)){
            found = userService.findByUsername(value);
        }else if("email".equalsIgnoreCase(field)){
            found = userService.findByEmail(value);
        }else if("phone".equalsIgnoreCase(field)){
            found = userService.findByPhone(value);
        }else{
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "Can't check uniqueness of user field: " + field);
        }
        if( found != null ){
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate field: " + field + " with value: " + value);
        }else{
            return new HashMap();
        }
    }
}
