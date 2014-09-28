package dnt.itsnow.model;

import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

/**
 * <h1>角色测试类</h1>
 */
public class RoleTest {

    private Role role;

    @Before
    public void setUp() throws Exception {
        role = new Role();
        role.setId(1L);
        role.setName("用户");
        role.setDescription("This is a test.");
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(role);
        Assert.assertNotNull(json);
    }

}
