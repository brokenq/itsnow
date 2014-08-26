/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import java.util.Set;

/**
 * <h1>工作时间</h1>
 *
 * 暂时被直接存在site里面
 */
public class WorkTime {
    //工作日: 1,2,3,4 这样，1代表周一，0代表周日
    private String workDays;
    // 开始时间，如 8:00
    private String startAt;
    // 结束时间，如 18:00
    private String endAt;

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
}
