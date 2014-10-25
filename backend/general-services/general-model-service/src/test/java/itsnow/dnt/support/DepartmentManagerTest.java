package itsnow.dnt.support;

import dnt.itsnow.model.Dictionary;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.service.DepartmentService;
import itsnow.dnt.config.DepartmentManagerConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Class Usage</h1>
 */
@ContextConfiguration(classes = DepartmentManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentManagerTest {

    @Autowired
    DepartmentService service;

    @Test
    public void testFindAllByTree() throws Exception {
        List<Department> departments = service.findAll("", true);
        Assert.assertNotNull(departments);
    }

    @Test
    public void testFindAllByNoTree() throws Exception {
        List<Department> departments = service.findAll("", false);
        Assert.assertNotNull(departments);
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
        department.setPosition(11L);
        department.setDescription("It's test.");
        department.creating();
        department.updating();

        Site site = new Site();
        site.setSn("10000");
        site.setName("大众五厂");
        Dictionary dictionary = new Dictionary();
        dictionary.setId(1L);
        site.setDictionary(dictionary);
        WorkTime workTime = new WorkTime();
        workTime.setId(1L);
        site.setWorkTime(workTime);
        site.setDescription("It's test.");
        site.creating();
        site.updating();
        List<Site> sites = new ArrayList<Site>();
        sites.add(site);
        department.setSites(sites);

        service.create(department);
        Assert.assertNotNull(department.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        String sn = "009";
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
        Dictionary dictionary = new Dictionary();
        dictionary.setId(1L);
        site.setDictionary(dictionary);
        WorkTime workTime = new WorkTime();
        workTime.setId(1L);
        site.setWorkTime(workTime);
        site.setDescription("It's test.");
        site.creating();
        site.updating();
        List<Site> sites = new ArrayList<Site>();
        sites.add(site);
        department.setSites(sites);

        service.update(department);
        department = service.findBySn(sn);
        Assert.assertTrue("it's a update test".equals(department.getDescription()));
    }

}
