/**
 * Developer: Kadvin Date: 14-9-15 下午1:12
 */
package dnt.itsnow.model;

/**
 * <h1>对应于Account的Client Json解析对象</h1>
 */
public class ClientAccount extends ClientConfigItem{
    private String type;
    private String sn;
    private String domain;
    private Long   userId; // 帐户管理员ID
    private ClientUser user;
    private ClientItsnowProcess process;
    private ClientAccountStatus status = ClientAccountStatus.New;

    public ClientItsnowProcess getProcess() {
        return process;
    }

    public void setProcess(ClientItsnowProcess process) {
        this.process = process;
    }

    public void setStatus(ClientAccountStatus status) {
        this.status = status;
    }

    public ClientUser getUser() {
        return user;
    }

    public void setUser(ClientUser user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
