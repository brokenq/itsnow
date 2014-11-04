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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Test the PublicServiceCatalog
 */
public class PublicServiceCatalogTest extends ValidatorSupport{
    PublicServiceCatalog catalog;

    ServiceCatalog childCatalog;

    @Before
    public void setUp() throws Exception {
        catalog = new PublicServiceCatalog();
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
        childCatalog.setParentId(1L);
        childCatalog.setLevel(2);

        List<ServiceCatalog> list = new ArrayList<ServiceCatalog>();
        list.add(childCatalog);
        catalog.setChildren(list);
    }

    @Test
    public void testValidationHappyCase() throws Exception {
        Set<ConstraintViolation<PublicServiceCatalog>> violations = validator.validate(catalog);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(catalog);
        System.out.println(json);
        PublicServiceCatalog parsed = JsonSupport.parseJson(json, PublicServiceCatalog.class);
        Assert.assertEquals(catalog, parsed);
    }

}
