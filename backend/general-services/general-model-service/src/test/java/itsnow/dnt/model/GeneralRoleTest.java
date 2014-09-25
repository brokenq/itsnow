package itsnow.dnt.model;

import dnt.itsnow.model.GeneralRole;
import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

/**
 * <h1>角色测试类</h1>
 */
public class GeneralRoleTest {

    private GeneralRole role;

    @Before
    public void setUp() throws Exception {
        role = new GeneralRole();
        role.setId(1L);
        role.setSn("001");
        role.setName("用户");
        role.setDescription("This is a test.");
        role.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        role.setUpdatedAt(role.getCreatedAt());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(role);
        System.out.println(json);
        GeneralRole parsed = JsonSupport.parseJson(json, GeneralRole.class);
        Assert.assertEquals(GeneralRole.class, parsed.getClass());
    }

}
