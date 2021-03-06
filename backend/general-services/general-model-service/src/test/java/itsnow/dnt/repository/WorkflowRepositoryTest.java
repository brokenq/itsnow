package itsnow.dnt.repository;

import dnt.itsnow.model.*;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.WorkflowRepository;
import itsnow.dnt.config.WorkflowRepositoryConfig;
import org.junit.Assert;
import org.junit.Before;
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

    PageRequest pageRequest;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testCreate() throws Exception {
        Workflow workflow = new Workflow();
        workflow.setName("工作流程一");
        workflow.setDescription("It's test.");
        workflow.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workflow.setUpdatedAt(workflow.getCreatedAt());

        Dictionary dictionary = new Dictionary();
        dictionary.setId(1L);
        workflow.setDictionary(dictionary);

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
        Workflow workflow = repository.findBySn(sn,Workflow.PRIVATE_SERVICE_ITEM);
        Assert.assertNotNull(workflow);
        repository.delete(sn);
        Assert.assertNull(repository.findBySn(sn,Workflow.PRIVATE_SERVICE_ITEM));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "003";
        Workflow workflow = repository.findBySn(sn,Workflow.PRIVATE_SERVICE_ITEM);
        repository.update(workflow);
        Assert.assertNotNull(workflow);
    }

    @Test
    public void testCount() throws Exception {
        Assert.assertNotNull(repository.count(Workflow.PRIVATE_SERVICE_ITEM, ""));
    }

    @Test
    public void testFind() throws Exception {
        Assert.assertNotNull(repository.find(Workflow.PRIVATE_SERVICE_ITEM, "", pageRequest));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        Assert.assertNotNull(repository.count(Workflow.PRIVATE_SERVICE_ITEM, "工作"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.assertNotNull(repository.find(Workflow.PRIVATE_SERVICE_ITEM, "工作", pageRequest));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(repository.findBySn("001", Workflow.PRIVATE_SERVICE_ITEM));
        Assert.assertNull(repository.findBySn("800", Workflow.PRIVATE_SERVICE_ITEM));
    }

}
