package itsnow.dnt.repository;

import dnt.itsnow.model.*;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.repository.StaffRepository;
import itsnow.dnt.config.StaffRepositoryConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>测试StaffRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = StaffRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class StaffRepositoryTest {

    @Autowired
    StaffRepository repository;

    PageRequest pageRequest;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testCreate() throws Exception {
        Staff staff = new Staff();
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
        repository.create(staff);
        Assert.assertNotNull(staff.getId());
    }

    @Test
    public void testDelete() throws Exception {
        String no = "005";
        repository.delete(no);
        Assert.assertNull(repository.findByNo(no));
    }

    @Test
    public void testUpdate() throws Exception {
        String no = "001";
        Staff staff = repository.findByNo(no);
        staff.setDescription("改变成新地址");
        repository.update(staff);
        Assert.assertNotNull(staff);
    }

    @Test
    public void testCount() throws Exception {
        Assert.assertNotNull(repository.count(""));
    }

    @Test
    public void testFind() throws Exception {
        List<Staff> staffs = repository.findAll("", pageRequest);
        Assert.assertNotNull(staffs);
    }

    @Test
    public void testFindByNo() throws Exception {
        Assert.assertNotNull(repository.findByNo("001"));
        Assert.assertNull(repository.findByNo("no exit of sn"));
    }

}
