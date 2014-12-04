package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * <h1>>服务采购方(MSU)或者服务供应方(MSP)在系统数据库中的账户</h1
 */

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
    @JsonSubTypes({
            @JsonSubTypes.Type(name = "base", value = Account.class),
            @JsonSubTypes.Type(name = "msc", value = MscAccount.class),
            @JsonSubTypes.Type(name = "msu", value = MsuAccount.class),
            @JsonSubTypes.Type(name = "msp", value = MspAccount.class)
    })
public abstract class AbstractAccount extends ConfigItem {

    private String type;
    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }
}
