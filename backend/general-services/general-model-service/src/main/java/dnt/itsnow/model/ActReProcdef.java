package dnt.itsnow.model;

/**
 * <h1>类功能说明</h1>
 */
public class ActReProcdef {

    private String id_;
    private int rev_;
    private String category_;
    private String name_;
    private String key_;
    private int version_;
    private String deploymentId_;
    private String resourceName_;
    private int hasStartFormKey_;
    private int suspensionState_;

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public int getRev_() {
        return rev_;
    }

    public void setRev_(int rev_) {
        this.rev_ = rev_;
    }

    public String getCategory_() {
        return category_;
    }

    public void setCategory_(String category_) {
        this.category_ = category_;
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public String getKey_() {
        return key_;
    }

    public void setKey_(String key_) {
        this.key_ = key_;
    }

    public int getVersion_() {
        return version_;
    }

    public void setVersion_(int version_) {
        this.version_ = version_;
    }

    public String getDeploymentId_() {
        return deploymentId_;
    }

    public void setDeploymentId_(String deploymentId_) {
        this.deploymentId_ = deploymentId_;
    }

    public String getResourceName_() {
        return resourceName_;
    }

    public void setResourceName_(String resourceName_) {
        this.resourceName_ = resourceName_;
    }

    public int getHasStartFormKey_() {
        return hasStartFormKey_;
    }

    public void setHasStartFormKey_(int hasStartFormKey_) {
        this.hasStartFormKey_ = hasStartFormKey_;
    }

    public int getSuspensionState_() {
        return suspensionState_;
    }

    public void setSuspensionState_(int suspensionState_) {
        this.suspensionState_ = suspensionState_;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ActReProcdef{");
        sb.append("id_='").append(id_).append('\'');
        sb.append(", rev_=").append(rev_);
        sb.append(", category_='").append(category_).append('\'');
        sb.append(", name_='").append(name_).append('\'');
        sb.append(", key_='").append(key_).append('\'');
        sb.append(", version_=").append(version_);
        sb.append(", deploymentId_='").append(deploymentId_).append('\'');
        sb.append(", resourceName_='").append(resourceName_).append('\'');
        sb.append(", hasStartFormKey_=").append(hasStartFormKey_);
        sb.append(", suspensionState_=").append(suspensionState_);
        sb.append('}');
        return sb.toString();
    }
}
