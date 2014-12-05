package itsnow.dnt.model;

import dnt.itsnow.model.*;
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
 * <h1>员工测试类</h1>
 */
public class StaffTest {

    static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    static Validator validator = factory.getValidator();

    private Staff staff;

    @Before
    public void setUp() throws Exception {
        staff = new Staff();
        staff.setId(1L);
        staff.setNo("008");
        staff.setName("王二麻子");
        staff.setMobilePhone("15901968888");
        staff.setFixedPhone("63557788");
        staff.setEmail("stone5751@126.com");
        staff.setTitle("攻城尸");
        staff.setType("合同工");
        staff.setStatus(StaffStatus.Normal);
        staff.setDescription("This is a test.");
        staff.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        staff.setUpdatedAt(staff.getCreatedAt());

        Department department = new Department();
        department.setId(1L);
        department.setSn("005");
        department.setName("公关部");
        department.setDescription("It's test.");
        department.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        department.setUpdatedAt(department.getCreatedAt());
        staff.setDepartment(department);

        Site site = new Site();
        site.setId(1L);
        staff.setSite(site);

        User user = new User();
        user.setId(1L);
        staff.setUser(user);
    }

    @Test
    public void testStaffIsNotValid() throws Exception {
        staff.setNo(null);
        Set<ConstraintViolation<Staff>> violations = validator.validate(staff);
        Assert.assertFalse(violations == null || violations.isEmpty());
    }

    @Test
    public void testStaffEmailIsNotValid() throws Exception {
        staff.setEmail("1234");
        Set<ConstraintViolation<Staff>> violations = validator.validate(staff);
        Assert.assertFalse(violations == null || violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(staff);
        Assert.assertNotNull(json);
    }

}
