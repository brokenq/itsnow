/**
 * Developer: Kadvin Date: 14-9-15 下午1:43
 */
package dnt.itsnow.model;

import java.io.File;
import java.util.Map;

/**
 * <h1>对应于AccountRegistration的Client Json解析对象</h1>
 */
public class ClientAccountRegistration {
    private String        type;
    private boolean asUser, asProvider;
    private ClientAccount account;
    private ClientUser    user;
    private Map<String, File> attachments;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAsUser() {
        return asUser;
    }

    public void setAsUser(boolean asUser) {
        this.asUser = asUser;
    }

    public boolean isAsProvider() {
        return asProvider;
    }

    public void setAsProvider(boolean asProvider) {
        this.asProvider = asProvider;
    }

    public ClientAccount getAccount() {
        return account;
    }

    public void setAccount(ClientAccount account) {
        this.account = account;
    }

    public ClientUser getUser() {
        return user;
    }

    public void setUser(ClientUser user) {
        this.user = user;
    }

    public Map<String, File> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, File> attachments) {
        this.attachments = attachments;
    }
}
