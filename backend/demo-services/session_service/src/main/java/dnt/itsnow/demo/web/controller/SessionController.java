/**
 * Developer: Kadvin Date: 14-7-10 下午8:56
 */
package dnt.itsnow.demo.web.controller;

import net.happyonroad.platform.web.controller.ApplicationController;
import dnt.itsnow.demo.exception.*;
import dnt.itsnow.demo.web.exception.*;
import dnt.itsnow.demo.web.model.LoginCredential;
import dnt.itsnow.demo.model.Session;
import dnt.itsnow.demo.api.SessionService;
import dnt.util.StringUtils;
import org.apache.http.auth.BasicUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * The Session Controller
 */
@RestController
@RequestMapping("/demo/session")
public class SessionController extends ApplicationController {
    @Autowired
    private SessionService sessionService;
    //显示给用户的当前验证码
    // 一般这个值为null，表明不显示验证码
    // 只有屡次登录不过才设置该值
    // 同时登录界面将会调用后端, 获取captcha图片展示给客户
    private String         currentCaptcha = null;

    /**
     * POST /api/session
     *
     * Try to login
     *
     * @param credential Login Credential
     * @return the session serialized as json
     */
    @RequestMapping(method = RequestMethod.POST)
    public Session create(@RequestBody LoginCredential credential,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        //TODO 应该用 interceptor来实现已经登录不能重复登录
        // 未登录的不能访问受保护资源
        Principal principal = (Principal) request.getSession().getAttribute("principal");
        if( principal != null ){
            throw new DuplicateAuthenticateException("You have been authenticated as " + principal.getName());
        }
        // captcha 验证失败
        if(StringUtils.isNotBlank(currentCaptcha)){
            if(StringUtils.isBlank(credential.getCaptcha())){
                throw new CaptchaException("The captcha is required");
            }else{
                if(!currentCaptcha.equalsIgnoreCase(credential.getCaptcha()))
                    throw new CaptchaException("The captcha is invalid");
            }
        }

        // 用户名密码验证
        Session session;
        try {
            session = sessionService.challenge(request.getRequestedSessionId(),
                                               credential.getUsername(),
                                               credential.getPassword());
        } catch (SessionException e) {
            throw new InvalidCredentialException("Your credential is invalid: " +
                                                 credential.getUsername() + ": " + e.getMessage());
        }

        int age = 60 * 60 * 24 * 30;
        // 记录会话标记
        request.getSession().setAttribute("sessionId", session.getSessionId());
        request.getSession().setAttribute("principal", new BasicUserPrincipal(session.getUserName()));
        // 记录用户名，以免下次输入
        Cookie username = new Cookie("username", session.getUserName());
        username.setMaxAge(age); // 30 days
        response.addCookie(username);

        // 记录用户，一个月内不再登录
        if( credential.isRemember() ){
            String code = sessionService.remember(session, credential.getMaxAge());
            Cookie remember = new Cookie("remember", code);
            remember.setMaxAge(age);
            response.addCookie(remember);
        }else{
            //等于移除相应的cookie
            Cookie remember = new Cookie("remember", "");
            response.addCookie(remember);
        }
        return session;
    }

    /**
     * GET /api/session
     *
     * GET current session information
     *
     * @return the session serialized as json
     */
    @RequestMapping(method = RequestMethod.GET)
    public Session current(HttpServletRequest request) {
        String sessionId = (String)request.getSession().getAttribute("sessionId");
        if( StringUtils.isBlank(sessionId ))
            throw new InvalidSessionException("You haven't provide any session id");
        Session session = sessionService.find(sessionId);
        if( session == null )
            throw new SessionExpiredException("Your session id: " + sessionId + " is expired");
        return session;
    }

    /**
     * DELETE /api/session
     *
     * Try to logout
     *
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public void destroy(HttpServletRequest request, HttpServletResponse response ) {
        try {
            Session currentSession = current(request);
            sessionService.destroy(currentSession);
            request.getSession().removeAttribute("sessionId");
            request.getSession().removeAttribute("principal");
        } catch (Exception e) {
            if( StringUtils.isNotBlank(request.getRequestedSessionId()))
                sessionService.destroy(request.getRequestedSessionId());
        }
        response.addCookie(new Cookie("username", ""));
        response.addCookie(new Cookie("remember", ""));
    }
}
