package itsnow.dnt.support;

import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.DepartmentService;
import itsnow.dnt.config.DepartmentManagerConfig;
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
@ContextConfiguration(classes = DepartmentManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentManagerTest {

    PageRequest pageRequest;

    @Autowired
    DepartmentService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }


    @Test
    public void testFindAll() throws Exception {

        Page<Department> departments = service.findAll("部", pageRequest);
        Assert.assertNotNull(departments.getTotalElements());
        Assert.assertNotNull(departments.getNumberOfElements());
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(service.findBySn("001"));
        Assert.assertNull(service.findBySn("800"));
    }

    @Test
    public void testCreate() throws Exception {
        Department department = new Department();
        department.setSn("006");
        department.setName("后勤保障部");
        department.setDescription("It's test.");
        department.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        department.setUpdatedAt(department.getCreatedAt());

        Site site = new Site();
        site.setSn("10000");
        site.setName("大众五厂");
        ProcessDictionary dictionary = new ProcessDictionary();
        dictionary.setId(1L);
        site.setProcessDictionary(dictionary);
        WorkTime workTime = new WorkTime();
        workTime.setId(1L);
        site.setWorkTime(workTime);
        site.setDescription("It's test.");
        site.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        site.setUpdatedAt(site.getCreatedAt());
        List<Site> sites = new ArrayList<Site>();
        sites.add(site);
        department.setSites(sites);

        service.create(department);
        Assert.assertNotNull(department.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        String sn = "003";
        Department department = service.findBySn(sn);
        Assert.assertNotNull(service.findBySn(sn));
        service.destroy(department);
        Assert.assertNull(service.findBySn(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "001";
        Department department = service.findBySn(sn);
        department.setDescription("it's a update test");

        Site site = new Site();
        site.setSn("10000");
        site.setName("大众五厂");
        ProcessDictionary dictionary = new ProcessDictionary();
        dictionary.setId(1L);
        site.setProcessDictionary(dictionary);
        WorkTime workTime = new WorkTime();
        workTime.setId(1L);
        site.setWorkTime(workTime);
        site.setDescription("It's test.");
        site.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        site.setUpdatedAt(site.getCreatedAt());
        List<Site> sites = new ArrayList<Site>();
        sites.add(site);
        department.setSites(sites);

        service.update(department);
        department = service.findBySn(sn);
        Assert.assertTrue(department.getDescription() == "it's a update test");
    }

}
