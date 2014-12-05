package dnt.itsnow.util;

import dnt.itsnow.model.ClientAccount;
import dnt.itsnow.model.ClientItsnowHost;
import dnt.itsnow.model.ClientItsnowProcess;

/**
 * 集成测试静态共享数据
 */
public class ShareDatas {

    public static final String PROCESS_START_INVOCATION_ID = "startInvocationId";
    public static final String PROCESS_STOP_INVOCATION_ID = "stopInvocationId";

    public static ClientAccount account;
    public static ClientItsnowHost host;
    public static ClientItsnowProcess process;
}
