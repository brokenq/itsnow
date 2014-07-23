/**
 * Developer: Kadvin Date: 14-7-10 下午9:06
 */
package dnt.itsnow.api;

import org.activiti.engine.*;

/**
 * The ActivitiEngine Service
 */
public interface ActivitiEngineService {

    RuntimeService getRuntimeService();

    RepositoryService getRepositoryService();

    ManagementService getManagementService();

    TaskService getTaskService();

    IdentityService getIdentityService();

    HistoryService getHistoryService();

    FormService getFormService();

    void test();
}
