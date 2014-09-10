package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

import java.sql.Timestamp;

/**
 * Created by jacky on 2014/7/28.
 */
public class Incident extends Record {

    private String number;//'故障单号，系统自动生成，生成规则:INC20140801111700001'
    private String requesterName;//'请求人'
    private String requesterLocation;//'请求人所处的地区'
    private String requesterEmail;//'请求人的email'
    private String requesterPhone;//'请求人的电话,
    private String serviceCatalog;// '服务目录'
    private String category;//'分类：软件、硬件、咨询、其他'
    private String impact;//'影响程度：高、中、低'
    private String urgency;//'紧急度：高、中、低'
    private String priority;//'优先级：高、中、低'
    private String requestType;//'请求类型：email,phone,web'
    private String ciType;//'CI类型'
    private String ci;//'CI'
    private String requestDescription;//'故障描述'
    private String createdBy;//'创建人'
    private String updatedBy;//'更新人'
    private String assignedUser;//'分配用户'
    private String assignedGroup;//'分配组'
    private Timestamp responseTime;//'响应时间'
    private Timestamp resolveTime;//'解决时间'
    private Timestamp closeTime;//'关闭时间'
    private String solution;//'解决方案'
    private String closeCode;//关闭代码

    private Boolean canProcess;//一线工程师是否可以处理该故障
    private Boolean resolved;//是否解决故障
    private Boolean hardwareError;//是否是硬件故障

    private String msuInstanceId;//对应msu activiti的instanceId
    private String msuAccountName;//msu account name
    private IncidentStatus msuStatus = IncidentStatus.Assigned;

    //add msp msuInstanceId,msp msuStatus
    private String mspInstanceId;//msp instance id
    private String mspAccountName;//msp account name
    private IncidentStatus mspStatus;//msp msuStatus

    public String getCloseCode() {
        return closeCode;
    }

    public void setCloseCode(String closeCode) {
        this.closeCode = closeCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterLocation() {
        return requesterLocation;
    }

    public void setRequesterLocation(String requesterLocation) {
        this.requesterLocation = requesterLocation;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public String getRequesterPhone() {
        return requesterPhone;
    }

    public void setRequesterPhone(String requesterPhone) {
        this.requesterPhone = requesterPhone;
    }

    public String getServiceCatalog() {
        return serviceCatalog;
    }

    public void setServiceCatalog(String serviceCatalog) {
        this.serviceCatalog = serviceCatalog;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getCiType() {
        return ciType;
    }

    public void setCiType(String ciType) {
        this.ciType = ciType;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getAssignedGroup() {
        return assignedGroup;
    }

    public void setAssignedGroup(String assignedGroup) {
        this.assignedGroup = assignedGroup;
    }

    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    public Timestamp getResolveTime() {
        return resolveTime;
    }

    public void setResolveTime(Timestamp resolveTime) {
        this.resolveTime = resolveTime;
    }

    public Timestamp getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Timestamp closeTime) {
        this.closeTime = closeTime;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getMsuInstanceId() {
        return msuInstanceId;
    }

    public void setMsuInstanceId(String msuInstanceId) {
        this.msuInstanceId = msuInstanceId;
    }

    public IncidentStatus getMsuStatus() {
        return msuStatus;
    }

    public void setMsuStatus(IncidentStatus msuStatus) {
        this.msuStatus = msuStatus;
    }

    public String getMsuAccountName() {
        return msuAccountName;
    }

    public void setMsuAccountName(String msuAccountName) {
        this.msuAccountName = msuAccountName;
    }

    public String getMspAccountName() {
        return mspAccountName;
    }

    public void setMspAccountName(String mspAccountName) {
        this.mspAccountName = mspAccountName;
    }

    public String getMspInstanceId() {
        return mspInstanceId;
    }

    public void setMspInstanceId(String mspInstanceId) {
        this.mspInstanceId = mspInstanceId;
    }

    public IncidentStatus getMspStatus() {
        return mspStatus;
    }

    public void setMspStatus(IncidentStatus mspStatus) {
        this.mspStatus = mspStatus;
    }

    public Boolean getCanProcess() {
        return canProcess;
    }

    public void setCanProcess(Boolean canProcess) {
        this.canProcess = canProcess;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }

    public Boolean getHardwareError() {
        return hardwareError;
    }

    public void setHardwareError(Boolean hardwareError) {
        this.hardwareError = hardwareError;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
