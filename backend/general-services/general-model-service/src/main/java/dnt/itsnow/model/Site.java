/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <h1>MSU/MSP的Site</h1>
 * 也是配置项
 */
public class Site extends ConfigItem {

    private String sn;
    //地址
    @NotBlank
    private String address;//TODO Address未来需要细化模型
    //工作时间
    @NotNull
    private WorkTime workTime;
    // 区域从字典中取的value
    private String area;
    // 部门
    private List<Department> departments;

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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Site{");
        sb.append("sn='").append(sn).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", workTime=").append(workTime);
        sb.append(", area=").append(area);
        sb.append(", departments=").append(departments);
        sb.append('}');
        return sb.toString();
    }
}
