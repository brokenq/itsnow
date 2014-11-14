package itsnow.dnt.support;

import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.WorkflowService;
import itsnow.dnt.config.WorkflowManagerConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

/**
 * <h1>Class Usage</h1>
 */
@ContextConfiguration(classes = WorkflowManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class WorkflowManagerTest {

    PageRequest pageRequest;

    @Autowired
    WorkflowService service;

    Workflow workflow;

    @Before
    public void setup() {
        pageRequest = new PageRequest(0, 1);

        workflow = new Workflow();
        workflow.setName("工作流程一");
        workflow.setDescription("It's test.");
        workflow.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workflow.setUpdatedAt(workflow.getCreatedAt());

        Dictionary dictionary = new Dictionary();
        dictionary.setId(1L);
        workflow.setDictionary(dictionary);

        ActReProcdef actReProcdef = new ActReProcdef();
        actReProcdef.setId("1");
        workflow.setActReProcdef(actReProcdef);

        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setId(1L);
        workflow.setServiceItem(serviceItem);
    }

    @Test
    @Ignore
    public void testCreate() throws Exception {
        service.create(workflow);
        Assert.assertNotNull(workflow.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        String sn = "005";
        Workflow workflow = service.findBySn(sn, Workflow.PUBLIC_SERVICE_ITEM);
        Assert.assertNotNull(workflow);
        service.destroy(workflow);
        Assert.assertNull(service.findBySn(sn, Workflow.PUBLIC_SERVICE_ITEM));
    }

    @Test
    public void testUpdate() throws Exception {
        Assert.assertNotNull(service.update(workflow));
    }

    @Test
    public void findAll() throws Exception {
        Page<Workflow> workflows = service.findAll("工作", pageRequest, Workflow.PUBLIC_SERVICE_ITEM);
        Assert.assertTrue(workflows.getContent().size() > 0);
    }

    @Test
    public void findBySn() throws Exception {
        Assert.assertNotNull(service.findBySn("001", Workflow.PUBLIC_SERVICE_ITEM));
    }

}
