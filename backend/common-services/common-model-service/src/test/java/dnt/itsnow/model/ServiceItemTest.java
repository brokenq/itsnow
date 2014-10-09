/**
 * Developer: Kadvin Date: 14-9-4 下午1:44
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Test the ServiceItem
 */
public class ServiceItemTest extends ValidatorSupport{
    ServiceItem item;

    ServiceCatalog catalog;

    @Before
    public void setUp() throws Exception {
        catalog = new ServiceCatalog();
        catalog.setId(1L);
        catalog.setSn("SN-001");
        catalog.setDescription("DESC-001");
        catalog.setTitle("TITLE-001");

        item = new ServiceItem();
        item.setId(1L);
        item.setTitle("title1");
        item.setDescription("desc1");
        item.setBrief("brief1");
        item.setCatalog(catalog);

    }

    @Test
    public void testValidationHappyCase() throws Exception {
        Set<ConstraintViolation<ServiceItem>> violations = validator.validate(item);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationCatalog() throws Exception {
        item.setCatalog(null);
        Set<ConstraintViolation<ServiceItem>> violations = validator.validate(item);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(item);
        System.out.println(json);
        ServiceItem parsed = JsonSupport.parseJson(json, ServiceItem.class);
        Assert.assertEquals(item, parsed);
    }

}
