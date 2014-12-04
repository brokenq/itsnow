/**
 * xiongjie on 14-8-11.
 */
package dnt.itsnow.it;

import dnt.itsnow.model.CsrfToken;
import org.junit.Assert;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * <h1>Security related integration test</h1>
 */
public class GlobalTest extends AbstractTest{

    @Test
    public void testSecurityCsrf() throws Exception {
        CsrfToken token = getForObject("/security/csrf", CsrfToken.class);
        Assert.assertNotNull(token);
        Assert.assertNotNull(token.getHeaderName());
        Assert.assertNotNull(token.getParameterName());
        Assert.assertNotNull(token.getToken());
    }

    @Test
    public void testGetRoutes() throws Exception {
        String routes = getForObject("/routes", String.class);
        String[] routesArray = StringUtils.split(routes, "\r");
        Assert.assertTrue(routesArray.length > 3);
    }
}
