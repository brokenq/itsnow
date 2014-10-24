/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

import org.hibernate.validator.constraints.NotBlank;

/**
 * <h1>流程字典对象</h1>
 * <p/>
 */
public class ProcessDictionary extends ConfigItem {


    @NotBlank
    private String code;
    @NotBlank
    private String display;
    @NotBlank
    private String val;
    @NotBlank
    private String state;
    private String type;
    private String sn;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProcessDictionary{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", display='").append(display).append('\'');
        sb.append(", val='").append(val).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
