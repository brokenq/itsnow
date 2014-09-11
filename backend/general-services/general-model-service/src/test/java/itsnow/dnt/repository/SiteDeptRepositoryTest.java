package itsnow.dnt.repository;

import dnt.itsnow.model.*;
import dnt.itsnow.repository.DepartmentRepository;
import dnt.itsnow.repository.SiteDeptRepository;
import dnt.itsnow.repository.SiteRepository;
import itsnow.dnt.config.SiteRepositoryConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

/**
 * <h1>测试SiteRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = SiteRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class SiteDeptRepositoryTest {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    SiteDeptRepository siteDeptRepository;

    @Test
    public void testCreate() throws Exception {
        Site site = siteRepository.findBySn("001");

        Department department = departmentRepository.findBySn("001");

        siteDeptRepository.create(new SiteDept(site, department));
        Assert.assertNotNull(site.getId());
    }

    @Test
    public void testDelete() throws Exception {
        long id = 6L;
        SiteDept siteDept = siteDeptRepository.findById(id);
        Assert.assertNotNull(siteDept);
        siteDeptRepository.deleteDeptAndSiteRelationByDeptId(siteDept.getDepartment().getId());
        siteDept = siteDeptRepository.findById(id);

        Assert.assertNull(siteDept);
    }

    @Test
    public void testUpdate() throws Exception {
        long id = 1L;
        SiteDept siteDept = siteDeptRepository.findById(id);

        Department department = departmentRepository.findBySn("002");
        siteDept.setDepartment(department);

        siteDeptRepository.update(siteDept);
        Assert.assertNotNull(siteDept.getDepartment());
    }

    @Test
    public void testFindById() throws Exception {
//        Assert.assertNotNull(siteDeptRepository.findById(1L));
        Assert.assertNull(siteDeptRepository.findById(800L));
    }

}
