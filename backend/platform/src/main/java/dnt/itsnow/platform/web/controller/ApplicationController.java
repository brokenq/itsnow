/**
 * Developer: Kadvin Date: 14-6-26 下午1:57
 */
package dnt.itsnow.platform.web.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * The Rest Controller
 */
@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ApplicationController {

    @RequestMapping("/v1/test")
    public Map<String, String> test(){
        Map<String,String> result = new HashMap<String, String>();
        result.put("key", "value");
        result.put("hello", "world");
        return result;
    }

}
