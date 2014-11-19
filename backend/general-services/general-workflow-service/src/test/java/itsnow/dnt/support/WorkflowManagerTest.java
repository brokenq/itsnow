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

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

/**
 * <h1>Class Usage</h1>
 */
@ContextConfiguration(classes = WorkflowManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
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
        workflow.setServiceItemType(Workflow.PUBLIC_SERVICE_ITEM);
        workflow.setDescription("It's test.");
        workflow.creating();
        workflow.updating();

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
    public void testCreate() throws Exception {
        service.create(workflow, new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        });
        Assert.assertNotNull(workflow.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        String sn = "005";
        Workflow workflow = service.findBySn(sn);
        Assert.assertNotNull(workflow);
        service.destroy(workflow);
        Assert.assertNull(service.findBySn(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        Assert.assertNotNull(service.update(workflow));
    }

    @Test
    public void findAll() throws Exception {
        Page<Workflow> workflows = service.findAll("工作", pageRequest);
        Assert.assertTrue(workflows.getContent().size() > 0);
    }

    @Test
    public void findBySn() throws Exception {
        Assert.assertNotNull(service.findBySn("001"));
    }

}
