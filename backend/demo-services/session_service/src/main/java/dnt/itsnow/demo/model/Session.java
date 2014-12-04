/**
 * Developer: Kadvin Date: 14-7-10 下午9:04
 */
package dnt.itsnow.demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.happyonroad.platform.model.Record;

import java.util.HashMap;
import java.util.Map;

/**
 * The user session
 */
public class Session extends Record{
    private String    sessionId;
    private String    userName;
    private String    nickName;

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
