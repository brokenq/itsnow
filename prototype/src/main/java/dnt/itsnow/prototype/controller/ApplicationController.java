/**
 * Developer: Kadvin Date: 14-6-26 下午1:57
 */
package dnt.itsnow.prototype.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

/**
 * A default Rest Controller
 */
@Controller
@Scope(WebApplicationContext.SCOPE_REQUEST)
@RequestMapping("/rest")
public class ApplicationController {
    @RequestMapping("home")
    public String home(){
        return "index";
    }

    @RequestMapping("register")
    public String register(){
        return "register";
    }

    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping("logout")
    public String logout(){
        return "logout";
    }
}
