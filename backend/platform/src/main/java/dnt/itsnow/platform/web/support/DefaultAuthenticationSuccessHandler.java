/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.platform.web.support;

import org.apache.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>用于处理认证成功的处理器</h1>
 */
public class DefaultAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response);
        response.setStatus(HttpStatus.SC_OK);

        response.setContentLength(targetUrl.getBytes().length);
        response.getWriter().append(targetUrl);
    }
}
