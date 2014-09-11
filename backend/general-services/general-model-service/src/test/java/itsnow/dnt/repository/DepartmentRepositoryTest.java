package itsnow.dnt.repository;

import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.repository.DepartmentRepository;
import dnt.itsnow.repository.SiteDeptRepository;
import itsnow.dnt.config.DepartmentRepositoryConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

/**
 * <h1>测试DepartmentRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = DepartmentRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository repository;

    @Autowired
    SiteDeptRepository siteDeptRepository;

    @Test
    public void testCreate() throws Exception {
        Department department = new Department();
        department.setSn("005");
        department.setName("公关部");
        department.setDescription("It's test.");
        department.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        department.setUpdatedAt(department.getCreatedAt());
        repository.create(department);
        Assert.assertNotNull(department.getId());
    }

    @Test
    public void testDelete() throws Exception {
        String sn = "004";
        Department department = repository.findBySn(sn);
        Assert.assertNotNull(department);

        siteDeptRepository.deleteDeptAndSiteRelationByDeptId(department.getId());

        repository.delete(sn);
        Assert.assertNull(repository.findBySn(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "003";
        Department department = repository.findBySn(sn);
        department.setDescription("修改测试");
        repository.update(department);
        Assert.assertEquals("修改测试", department.getDescription());
    }

    @Test
    public void testCount() throws Exception {
        Assert.assertNotNull(repository.count());
    }

    @Test
    public void testFind() throws Exception {
        Assert.assertNotNull(repository.find("updated_at", "desc",  0, 10));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        Assert.assertNotNull(repository.countByKeyword("%部%"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.assertNotNull(repository.findByKeyword("%部%","updated_at","desc", 0, 10));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(repository.findBySn("001"));
        Assert.assertNull(repository.findBySn("800"));
    }

}
