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
    private int version;
    private ActReProcdef actReProcdef;
    private ServiceItem serviceItem;
    private String serviceTtemType;
    private ProcessDictionary processDictionary;

    /** 公共服务目录标记 */
    public static final String PUBLIC_SERVICE_ITEM = "0";

    /** 私有服务目录标记 */
    public static final String PRIVATE_SERVICE_ITEM = "1";

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public ActReProcdef getActReProcdef() {
        return actReProcdef;
    }

    public void setActReProcdef(ActReProcdef actReProcdef) {
        this.actReProcdef = actReProcdef;
    }

    public ProcessDictionary getProcessDictionary() {
        return processDictionary;
    }

    public void setProcessDictionary(ProcessDictionary processDictionary) {
        this.processDictionary = processDictionary;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getServiceTtemType() {
        return serviceTtemType;
    }

    public void setServiceTtemType(String serviceTtemType) {
        this.serviceTtemType = serviceTtemType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Workflow{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", actReProcdef=").append(actReProcdef);
        sb.append(", serviceItem=").append(serviceItem);
        sb.append(", serviceTtemType='").append(serviceTtemType).append('\'');
        sb.append(", processDictionary=").append(processDictionary);
        sb.append('}');
        return sb.toString();
    }

}
