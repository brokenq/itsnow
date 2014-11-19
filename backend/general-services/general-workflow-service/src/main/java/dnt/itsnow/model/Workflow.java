/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

/**
 * <h1>工作流</h1>
 * <p></p>
 */
public class Workflow extends ConfigItem {

    private String sn;
    private ActReProcdef actReProcdef;
    private ActReDeployment actReDeployment;
    private ServiceItem serviceItem;
    private String serviceItemSn;
    private String serviceItemType;
    private Dictionary dictionary;

    public String getServiceItemSn() {
        return serviceItemSn;
    }

    public void setServiceItemSn(String serviceItemSn) {
        this.serviceItemSn = serviceItemSn;
    }

    /** 公共服务目录标记 */
    public static final String PUBLIC_SERVICE_ITEM = "0";

    /** 私有服务目录标记 */
    public static final String PRIVATE_SERVICE_ITEM = "1";

    public ActReProcdef getActReProcdef() {
        return actReProcdef;
    }

    public void setActReProcdef(ActReProcdef actReProcdef) {
        this.actReProcdef = actReProcdef;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getServiceItemType() {
        return serviceItemType;
    }

    public void setServiceItemType(String serviceItemType) {
        this.serviceItemType = serviceItemType;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public ActReDeployment getActReDeployment() {
        return actReDeployment;
    }

    public void setActReDeployment(ActReDeployment actReDeployment) {
        this.actReDeployment = actReDeployment;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Workflow{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", actReProcdef=").append(actReProcdef);
        sb.append(", actReDeployment=").append(actReDeployment);
        sb.append(", serviceItem=").append(serviceItem);
        sb.append(", serviceItemSn='").append(serviceItemSn).append('\'');
        sb.append(", serviceItemType='").append(serviceItemType).append('\'');
        sb.append(", dictionary=").append(dictionary);
        sb.append('}');
        return sb.toString();
    }

}
