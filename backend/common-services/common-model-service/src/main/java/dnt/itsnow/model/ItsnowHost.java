/**
 * Developer: Kadvin Date: 14-8-27 下午12:17
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

/**
 * The itsnow host which can service for MSC/MSU/MSP
 */
public class ItsnowHost extends DeployResource implements Comparable<ItsnowHost>
{
    @NotNull
    private String address;
    private HostStatus status = HostStatus.Planing;
    //这台主机一般能部署多少个Itsnow Process
    private int    capacity;
    @NotNull
    private HostType type = HostType.APP;
    // 拓展字段，不存储于数据库中，用于列表中展示
    private HostExtend extend;

    @Override
    @NotNull
    public String getName() {
        return super.getName();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HostStatus getStatus() {
        return status;
    }

    public void setStatus(HostStatus status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public HostType getType() {
        return type;
    }

    public void setType(HostType type) {
        this.type = type;
    }

    public HostExtend getExtend() {
        return extend;
    }

    public void setExtend(HostExtend extend) {
        this.extend = extend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItsnowHost)) return false;
        if (!super.equals(o)) return false;

        ItsnowHost that = (ItsnowHost) o;

        if (capacity != that.capacity) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (extend != null ? !extend.equals(that.extend) : that.extend != null) return false;
        //noinspection RedundantIfStatement
        if (status != that.status) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (extend != null ? extend.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + capacity;
        return result;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(ItsnowHost another) {
        return getLeftCapacity() - another.getLeftCapacity();
    }

    @JsonIgnore
    public int getLeftCapacity() {
        int processesCount = extend == null ? 0 : extend.getProcessesCount();
        int schemasCount = extend == null ? 0 : extend.getSchemasCount();
        return this.getCapacity() - processesCount - schemasCount;
    }
}
