/**
 * Developer: Kadvin Date: 14-9-4 下午1:44
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import net.happyonroad.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Test the ServiceCatalog
 */
public class ServiceCatalogTest extends ValidatorSupport{
    ServiceCatalog catalog;

    ServiceCatalog childCatalog;

    @Before
    public void setUp() throws Exception {
        catalog = new ServiceCatalog();
        catalog.setId(1L);
        catalog.setSn("SN-001");
        catalog.setDescription("DESC-001");
        catalog.setTitle("TITLE-001");
        catalog.setLevel(1);

        childCatalog = new ServiceCatalog();
        childCatalog.setId(2L);
        childCatalog.setSn("SN-001-01");
        childCatalog.setDescription("DESC-001-01");
        childCatalog.setTitle("TITLE-001-01");
        childCatalog.setLevel(2);
        childCatalog.setParentId(1L);

        List<ServiceCatalog> list = new ArrayList<ServiceCatalog>();
        list.add(childCatalog);
        catalog.setChildren(list);
    }

    @Test
    public void testValidationHappyCase() throws Exception {
        Set<ConstraintViolation<ServiceCatalog>> violations = validator.validate(catalog);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(catalog);
        System.out.println(json);
        ServiceCatalog parsed = JsonSupport.parseJson(json, ServiceCatalog.class);
        Assert.assertEquals(catalog, parsed);
    }

}
