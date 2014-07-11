/**
 * Developer: Kadvin Date: 14-6-26 下午1:57
 */
package dnt.itsnow.backend.web.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

/**
 * The Rest Controller
 */
@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ApplicationController {
}
