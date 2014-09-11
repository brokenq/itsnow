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
    private String level;
    @NotBlank
    private String levelName;
    @NotBlank
    private String state;
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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

    @Override
    public String toString() {
        return "ProcessDictionary{" +
                "code='" + code + '\'' +
                ", level='" + level + '\'' +
                ", levelName='" + levelName + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
