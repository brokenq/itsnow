package dnt.itsnow.model;

/**
 * <h1>MSP工作流</h1>
 */
public class MspWorkflow extends Workflow {

    private PublicServiceItem publicServiceItem;

    public PublicServiceItem getPublicServiceItem() {
        return publicServiceItem;
    }

    public void setPublicServiceItem(PublicServiceItem publicServiceItem) {
        this.publicServiceItem = publicServiceItem;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MspWorkflow{");
        sb.append("publicServiceItem=").append(publicServiceItem);
        sb.append('}');
        return sb.toString();
    }
}
