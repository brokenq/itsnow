package dnt.itsnow.model;


import java.util.Properties;

/**
 * <h1>对应于 ItsnowHosts 的Client Json解析对象</h1>
 */
public class ClientItsnowHost extends ClientRecord {

    private String address;
    private ClientHostStatus status = ClientHostStatus.Planing;
    //这台主机一般能部署多少个Itsnow Process
    private int    capacity;
    private ClientHostType type = ClientHostType.APP;

    private int processesCount;
    private int schemasCount;

    private Properties configuration;
    private String description;
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ClientHostStatus getStatus() {
        return status;
    }

    public void setStatus(ClientHostStatus status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ClientHostType getType() {
        return type;
    }

    public void setType(ClientHostType type) {
        this.type = type;
    }

    public int getProcessesCount() {
        return processesCount;
    }

    public void setProcessesCount(int processesCount) {
        this.processesCount = processesCount;
    }

    public int getSchemasCount() {
        return schemasCount;
    }

    public void setSchemasCount(int schemasCount) {
        this.schemasCount = schemasCount;
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
