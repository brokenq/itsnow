/**
 * xiongjie on 14-8-11.
 */
package dnt.itsnow.it;

import dnt.itsnow.model.User;
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
        User user = withLoginUser(new Callback<User>() {
            @Override
            public User perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForObject("/api/profile", User.class, request);
            }
        });
        Assert.assertNotNull(user);
        Assert.assertEquals(configuration.getUsername(), user.getUsername());
    }
}
