package dnt.itsnow.config;


import dnt.itsnow.platform.config.DefaultServiceConfig;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.WorkflowService;

public class MspWorkflowServiceConfig extends DefaultServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        importService(CommonUserService.class, "*", "plainUserService");
        importService(WorkflowService.class);
    }
}
