package dnt.itsnow.model;

/**
 * <h1>MSP工作流</h1>
 */
public class MspWorkflow extends Workflow {

    private PrivateServiceItem privateServiceItem;

    public PrivateServiceItem getPrivateServiceItem() {
        return privateServiceItem;
    }

    public void setPrivateServiceItem(PrivateServiceItem privateServiceItem) {
        this.privateServiceItem = privateServiceItem;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MspWorkflow{");
        sb.append("privateServiceItem=").append(privateServiceItem);
        sb.append('}');
        return sb.toString();
    }
}
