/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

/**
 * <h1>工作时间</h1>
 * <p></p>
 */
public class WorkTime extends ConfigItem {

   // @NotBlank
    private String sn;
    //工作日: 1,2,3,4 这样，1代表周一，0代表周日
   // @NotBlank
    private String workDays;
    // 开始时间，如 8:00
    @NotBlank
    private String startAt;
    // 结束时间，如 18:00
    @NotBlank
    private String endAt;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getWorkDays() {
        return workDays;
    }

    public void setWorkDays(String workDays) {
        this.workDays = workDays;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WorkTime{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", workDays='").append(workDays).append('\'');
        sb.append(", startAt='").append(startAt).append('\'');
        sb.append(", endAt='").append(endAt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
