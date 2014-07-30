/**
 * Developer: Kadvin Date: 14-7-14 下午3:22
 */
package dnt.itsnow.demo.model;

import dnt.itsnow.platform.model.Record;

/**
 * the sample User
 */
public class User extends Record {
    private String name, nickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
