package dnt.itsnow.model;

/**
 * <h1>类功能说明</h1>
 */
public class ActReProcdef {

    private String id;
    private int rev;
    private String category;
    private String name;
    private String key;
    private int version;
    private String deploymentId;
    private String resourceName;
    private int hasStartFormKey;
    private int suspensionState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRev() {
        return rev;
    }

    public void setRev(int rev) {
        this.rev = rev;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getHasStartFormKey() {
        return hasStartFormKey;
    }

    public void setHasStartFormKey(int hasStartFormKey) {
        this.hasStartFormKey = hasStartFormKey;
    }

    public int getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(int suspensionState) {
        this.suspensionState = suspensionState;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ActReProcdef{");
        sb.append("id='").append(id).append('\'');
        sb.append(", rev=").append(rev);
        sb.append(", category='").append(category).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", version=").append(version);
        sb.append(", deploymentId='").append(deploymentId).append('\'');
        sb.append(", resourceName='").append(resourceName).append('\'');
        sb.append(", hasStartFormKey=").append(hasStartFormKey);
        sb.append(", suspensionState=").append(suspensionState);
        sb.append('}');
        return sb.toString();
    }
}
