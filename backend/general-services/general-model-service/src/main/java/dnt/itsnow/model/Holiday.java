/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

/**
 * <h1>节假日</h1>
 * <p></p>
 */
public class Holiday extends ConfigItem{

    //节日规则
    private String rule;
    // 开始时间
    private String startedAt;
    // 结束时间
    private String endAt;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "rule='" + rule + '\'' +
                ", startedAt='" + startedAt + '\'' +
                ", endAt='" + endAt + '\'' +
                '}';
    }

}
