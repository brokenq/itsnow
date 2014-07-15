/**
 * Developer: Kadvin Date: 14-7-10 下午9:04
 */
package dnt.itsnow.services.model;

/**
 * The user session
 */
public class Session {
    private String sessionId;
    private String username;
    private String nickName;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
