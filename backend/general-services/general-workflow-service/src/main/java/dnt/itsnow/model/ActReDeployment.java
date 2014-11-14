package dnt.itsnow.model;

import java.sql.Timestamp;

/**
 * <h1>工作流部署类</h1>
 */
public class ActReDeployment {

    private String id;
    private String name;
    private String category;
    private String tenantId;
    private Timestamp deployTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Timestamp getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Timestamp deployTime) {
        this.deployTime = deployTime;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ActReDeployment{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", tenantId='").append(tenantId).append('\'');
        sb.append(", deployTime=").append(deployTime);
        sb.append('}');
        return sb.toString();
    }
}
