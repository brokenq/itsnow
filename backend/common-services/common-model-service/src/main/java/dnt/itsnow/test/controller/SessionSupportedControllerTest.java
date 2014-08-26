/**
 * xiongjie on 14-8-7.
 */
package dnt.itsnow.test.controller;

import dnt.itsnow.model.MspAccount;
import dnt.itsnow.model.User;
import org.junit.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/**
 * <h1>支持对Session Support Controller的子类进行测试</h1>
 *
 * 主要是: 为web请求设定伪造的user principal
 */
public class SessionSupportedControllerTest extends ApplicationControllerTest {
    protected Authentication currentUserPrincipal;

    @Before
    public void setupSessionSupported() {
        User currentUser = new User();
        currentUser.setUsername("test");
        currentUser.setPassword("secret");
        currentUser.setAccount(new MspAccount());
        currentUserPrincipal = new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword());
    }

    @Override
    protected MockHttpServletRequestBuilder decorate(MockHttpServletRequestBuilder request) {
        request.principal(currentUserPrincipal);
        return super.decorate(request);
    }
}
