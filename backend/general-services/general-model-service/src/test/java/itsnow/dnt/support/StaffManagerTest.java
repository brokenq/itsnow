package itsnow.dnt.support;

import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.StaffService;
import itsnow.dnt.config.StaffManagerConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Class Usage</h1>
 */
@ContextConfiguration(classes = StaffManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class StaffManagerTest {

    PageRequest pageRequest;

    @Autowired
    StaffService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<Staff> staffs = service.findAll("", pageRequest);
        Assert.assertNotNull(staffs.getTotalElements());
        Assert.assertNotNull(staffs.getNumberOfElements());
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(service.findByNo("005"));
        Assert.assertNull(service.findByNo("800"));
    }

    @Test
    public void testCreate() throws Exception {
        Staff staff = new Staff();
        staff.setNo("009");
        staff.setName("王二麻子");
        staff.setMobilePhone("15901968888");
        staff.setFixedPhone("63557788");
        staff.setEmail("stone5751@126.com");
        staff.setTitle("攻城尸");
        staff.setType("合同工");
        staff.setStatus("1");
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

        service.create(staff);
        Assert.assertNotNull(staff.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        Staff staff = service.findByNo("007");
        service.destroy(staff);
        Assert.assertNull(service.findByNo(staff.getNo()));
    }

    @Test
    public void testUpdate() throws Exception {
        Page<Staff> staffs = service.findAll("", pageRequest);
        Staff staff = staffs.getContent().get(0);
        staff.setDescription("Hello World!");
        service.update(staff);
        staff = service.findByNo(staff.getNo());
        Assert.assertTrue(staff.getDescription() == "Hello World!");
    }

}
