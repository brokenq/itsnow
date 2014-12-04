package itsnow.dnt.model;

import dnt.itsnow.model.Site;
import net.happyonroad.support.JsonSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.util.Set;

/**
 * <h1>地点测试类</h1>
 */
public class SiteTest {

    static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    static Validator validator = factory.getValidator();

    private Site site;

    @Before
    public void setUp() throws Exception {
        site = new Site();
        this.site.setId(1L);
        this.site.setSn("001");
        this.site.setName("大众一厂");
        this.site.setAddress("上海市安亭洛浦路63号");
        this.site.setDescription("This is a test.");
        this.site.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.site.setUpdatedAt(this.site.getCreatedAt());
    }

    @Test
    public void testSiteIsNotValid() throws Exception {
        site.setSn("001");
        site.setAddress(null);
        Set<ConstraintViolation<Site>> violations = validator.validate(site);
        Assert.assertFalse(violations == null || violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(site);
        System.out.println(json);
        Site parsed = JsonSupport.parseJson(json, Site.class);
        Assert.assertEquals(Site.class, parsed.getClass());
    }

}
