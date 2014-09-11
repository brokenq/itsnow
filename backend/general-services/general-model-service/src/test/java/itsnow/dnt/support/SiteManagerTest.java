/**
 * xiongjie on 14-8-6.
 */
package itsnow.dnt.support;

import dnt.itsnow.model.Department;
import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.model.Site;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.SiteService;
import itsnow.dnt.config.SiteManagerConfig;
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
@ContextConfiguration(classes = SiteManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class SiteManagerTest {

    PageRequest pageRequest;

    @Autowired
    SiteService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }


    @Test
    public void testFindAll() throws Exception {

        Page<Site> sites = service.findAll("工厂", pageRequest);
        Assert.assertEquals(1, sites.getTotalElements());
        Assert.assertEquals(1, sites.getNumberOfElements());
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(service.findBySn("001"));
        Assert.assertNull(service.findBySn("800"));
    }

    @Test
    public void testCreate() throws Exception {

        Site site = new Site();
        site.setSn("10000");
        site.setName("大众五厂");
        site.setDescription("It's test.");
        site.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        site.setUpdatedAt(site.getCreatedAt());

        ProcessDictionary dictionary = new ProcessDictionary();
        dictionary.setId(1L);
        site.setProcessDictionary(dictionary);

        WorkTime workTime = new WorkTime();
        workTime.setId(1L);
        site.setWorkTime(workTime);

        Department department = new Department();
        department.setId(1L);
        department.setSn("006");
        department.setName("后勤保障部");
        department.setDescription("It's test.");
        department.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        department.setUpdatedAt(department.getCreatedAt());
        List<Department> departments = new ArrayList<Department>();
        departments.add(department);
        site.setDepartments(departments);

        service.create(site);
        Assert.assertNotNull(site.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        String sn = "003";
        Site site = service.findBySn(sn);
        Assert.assertNotNull(service.findBySn(sn));
        service.destroy(site);
        Assert.assertNull(service.findBySn(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "001";
        Site site = service.findBySn(sn);
        site.setDescription("it's a update test");

        Department department = new Department();
        department.setId(1L);
        department.setSn("006");
        department.setName("后勤保障部");
        department.setDescription("It's test.");
        department.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        department.setUpdatedAt(department.getCreatedAt());
        List<Department> departments = new ArrayList<Department>();
        departments.add(department);
        site.setDepartments(departments);

        service.update(site);
        site = service.findBySn(sn);
        Assert.assertTrue(site.getDescription() == "it's a update test");
    }

}
