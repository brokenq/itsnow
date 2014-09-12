package itsnow.dnt.repository;

import dnt.itsnow.model.*;
import dnt.itsnow.repository.StaffRepository;
import itsnow.dnt.config.StaffRepositoryConfig;
import org.junit.Assert;
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

    @Test
    public void testCreate() throws Exception {
        Staff staff = new Staff();
        staff.setId(1L);
        staff.setNo("008");
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
        repository.create(staff);
        Assert.assertNotNull(staff.getId());
    }

    @Test
    public void testDelete() throws Exception {
        List<Staff> staffs = repository.find("updated_at", "desc", 0, 10);
        String no = staffs.get(0).getNo();
        repository.delete(no);
        Assert.assertNull(repository.findByNo(no));
    }

    @Test
    public void testUpdate() throws Exception {
        List<Staff> staffs = repository.find("updated_at", "desc", 0, 10);
        String no = staffs.get(0).getNo();
        Staff staff = repository.findByNo(no);
        staff.setDescription("改变成新地址");
        repository.update(staff);
        Assert.assertNotNull(staff);
    }

    @Test
    public void testCount() throws Exception {
        Assert.assertNotNull(repository.count());
    }

    @Test
    public void testFind() throws Exception {
        List<Staff> staffs = repository.find("updated_at", "desc",  0, 10);
        Assert.assertNotNull(staffs);
    }

    @Test
    public void testCountByKeyword() throws Exception {
        Assert.assertNotNull(repository.countByKeyword("%钱%"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.assertNotNull(repository.findByKeyword("%钱%","updated_at","desc", 0, 10));
    }

    @Test
    public void testFindBySn() throws Exception {
        List<Staff> staffs = repository.find("updated_at", "desc", 0, 10);
        String no = staffs.get(0).getNo();
        Assert.assertNotNull(repository.findByNo(no));
        Assert.assertNull(repository.findByNo(no+""+no));
    }

}
