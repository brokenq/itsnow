package dnt.itsnow.model;

import java.util.Properties;

/**
 * Itsnow DB Schema
 */
public class ClientItsnowSchema extends ClientConfigItem {
    private Long hostId;
    private ClientItsnowHost host;
    private ClientItsnowProcess process;
    private Properties configuration;

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public ClientItsnowHost getHost() {
        return host;
    }

    public void setHost(ClientItsnowHost host) {
        this.host = host;
    }

    public ClientItsnowProcess getProcess() {
        return process;
    }

    public void setProcess(ClientItsnowProcess process) {
        this.process = process;
    }
}
