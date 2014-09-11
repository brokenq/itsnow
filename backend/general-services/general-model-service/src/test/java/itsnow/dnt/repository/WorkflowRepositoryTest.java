package itsnow.dnt.repository;

import dnt.itsnow.model.*;
import dnt.itsnow.repository.WorkflowRepository;
import itsnow.dnt.config.WorkflowRepositoryConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

/**
 * <h1>测试WorkflowRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = WorkflowRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class WorkflowRepositoryTest {

    @Autowired
    WorkflowRepository repository;

    @Test
    public void testCreate() throws Exception {
        Workflow workflow = new Workflow();
        workflow.setName("工作流程一");
        workflow.setDescription("It's test.");
        workflow.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workflow.setUpdatedAt(workflow.getCreatedAt());

        ProcessDictionary dictionary = new ProcessDictionary();
        dictionary.setId(1L);
        workflow.setProcessDictionary(dictionary);

        ActReProcdef actReProcdef = new ActReProcdef();
        actReProcdef.setId_("1");
        workflow.setActReProcdef(actReProcdef);

        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setId(1L);
        workflow.setServiceItem(serviceItem);

        repository.create(workflow);
        Assert.assertNotNull(workflow.getId());
    }

    @Test
    public void testDelete() throws Exception {
        String sn = "006";
        Workflow workflow = repository.findBySn(sn,"1");
        Assert.assertNotNull(workflow);
        repository.delete(sn);
        Assert.assertNull(repository.findBySn(sn,"1"));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "003";
        Workflow workflow = repository.findBySn(sn,"1");
        repository.update(workflow);
        Assert.assertNotNull(workflow);
    }

    @Test
    public void testCount() throws Exception {
        Assert.assertNotNull(repository.count());
    }

    @Test
    public void testFind() throws Exception {
        Assert.assertNotNull(repository.find("1", "updated_at", "desc",  0, 10));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        Assert.assertNotNull(repository.countByKeyword("%工作%"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.assertNotNull(repository.findByKeyword("1", "%工作%","updated_at","desc", 0, 10));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(repository.findBySn("001","1"));
        Assert.assertNull(repository.findBySn("800","1"));
    }

}
