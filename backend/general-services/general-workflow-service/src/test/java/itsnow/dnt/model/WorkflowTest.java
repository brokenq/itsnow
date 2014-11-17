package itsnow.dnt.model;

import dnt.itsnow.model.Workflow;
import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <h1>工作流程实体测试类</h1>
 */
public class WorkflowTest {

    private Workflow workflow;

    @Before
    public void setUp() throws Exception {
        workflow = new Workflow();
        this.workflow.setId(1L);
        this.workflow.setSn("0001");
        this.workflow.setName("工作流程一");
        this.workflow.setDescription("This is a test.");
        this.workflow.creating();
        this.workflow.updating();
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(workflow);
        System.out.println(json);
        Assert.assertNotNull(json);
    }

}
