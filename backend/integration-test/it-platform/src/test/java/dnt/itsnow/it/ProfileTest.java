/**
 * xiongjie on 14-8-11.
 */
package dnt.itsnow.it;

import dnt.itsnow.model.ClientUser;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * <h1>当前用户Profile的测试用例</h1>
 */
public class ProfileTest extends AbstractTest{

    // 测试用户读取自身profile的url
    @Test
    public void testGetProfile() throws Exception {
        ClientUser user = withLoginUser(new Callback<ClientUser>() {
            @Override
            public ClientUser perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForObject("/api/profile", ClientUser.class, request);
            }
        });
        Assert.assertNotNull(user);
        Assert.assertEquals(configuration.getUsername(), user.getUsername());
    }
}
