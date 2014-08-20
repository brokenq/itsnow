/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import java.util.Set;

/**
 * <h1>MSU/MSP的Site</h1>
 * 也是配置项
 */
public class Site extends ConfigItem {
    //地址
    private String address;//TODO Address未来需要细化模型
    //工作时间
    private WorkTime workTime;
    //节假日
    private Set<Holiday> holidays;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public WorkTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTime workTime) {
        this.workTime = workTime;
    }

    public Set<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(Set<Holiday> holidays) {
        this.holidays = holidays;
    }
}
