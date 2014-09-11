package itsnow.dnt.support;

import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.MspWorkflowService;
import itsnow.dnt.config.MspWorkflowManagerConfig;
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
 * <h1>Class Usage</h1>
 */
@ContextConfiguration(classes = MspWorkflowManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MspWorkflowManagerTest {

    @Autowired
    MspWorkflowService service;

    Workflow workflow;

    PageRequest pageRequest;

    @Before
    public void setup() {

        pageRequest = new PageRequest(0, 1);

        workflow = new Workflow();
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
    }

    @Test
    public void testFindAll() throws Exception {

        Page<Workflow> workflows = service.findAll("工作", pageRequest);
        Assert.assertNotNull(workflows.getTotalElements());
        Assert.assertNotNull(workflows.getNumberOfElements());
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(service.findBySn("001"));
        Assert.assertNull(service.findBySn("800"));
    }

    @Test
    public void testCreate() throws Exception {
        service.create(workflow);
        Assert.assertNotNull(workflow.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        Assert.assertNotNull(service.destroy(workflow));
    }

    @Test
    public void testUpdate() throws Exception {
        Assert.assertNotNull(service.update(workflow));
    }

}
