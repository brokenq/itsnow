/**
 * Developer: Kadvin Date: 14-7-10 下午9:04
 */
package dnt.itsnow.services.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * The user session
 */
public class Session {
    protected static ObjectMapper mapper = new ObjectMapper();
    private String    sessionId;
    private String    userName;
    private String    nickName;
    private Timestamp createdAt, updatedAt;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Timestamp getCreatedAt() {
        return createdAt == null ? new Timestamp(System.currentTimeMillis()) : createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt == null ? new Timestamp(System.currentTimeMillis()) : updatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getData(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", getUserName());
        map.put("nickName", getNickName());
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public void setData(String data){
        try {
            //noinspection unchecked
            Map<String, Object> map = (Map<String, Object>)mapper.readValue(data, Map.class);
            setUserName((String) map.get("userName"));
            setNickName((String) map.get("nickName"));
        } catch (Exception e) {
            //ignore
        }
    }
}
