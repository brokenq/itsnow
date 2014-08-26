package dnt.itsnow.model;

import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by Sin on 2014/8/25.
 */
public class MenuItemTest {

    static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    static Validator validator = factory.getValidator();

    MenuItem menuItem;

    @Before
    public void setUp() throws Exception {
        menuItem = new MenuItem();
        this.menuItem.setId(1L);
        this.menuItem.setName("用户");
        this.menuItem.setUrl("index.user");
        this.menuItem.setType("0");
        this.menuItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.menuItem.setUpdatedAt(this.menuItem.getCreatedAt());
    }

    @Test
    public void testEmptyMenuItemIsNotValid() throws Exception {
        menuItem.setName(null);
        menuItem.setUrl(null);
        menuItem.setType(null);
        Set<ConstraintViolation<MenuItem>> violations = validator.validate(menuItem);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testCorrectMenuItemIsValid() throws Exception {
        menuItem.setName("用户");
        menuItem.setUrl("index.user");
        menuItem.setType("0");
        Set<ConstraintViolation<MenuItem>> violations = validator.validate(menuItem);
        Assert.assertTrue(violations == null || violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(menuItem);
        System.out.println(json);
        MenuItem parsed = JsonSupport.parseJson(json, MenuItem.class);
        Assert.assertTrue(parsed instanceof MenuItem);
    }

}
