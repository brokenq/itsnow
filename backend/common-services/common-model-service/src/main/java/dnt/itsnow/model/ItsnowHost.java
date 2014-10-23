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

    private int processesCount;
    private int schemasCount;

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

    public int getProcessesCount() {
        return processesCount;
    }

    public void setProcessesCount(int processesCount) {
        this.processesCount = processesCount;
    }

    public int getSchemasCount() {
        return schemasCount;
    }

    public void setSchemasCount(int schemasCount) {
        this.schemasCount = schemasCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItsnowHost)) return false;
        if (!super.equals(o)) return false;

        ItsnowHost that = (ItsnowHost) o;

        if (capacity != that.capacity) return false;
        if (processesCount != that.processesCount) return false;
        if (schemasCount != that.schemasCount) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
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
        result = 31 * result + status.hashCode();
        result = 31 * result + capacity;
        result = 31 * result + processesCount;
        result = 31 * result + schemasCount;
        return result;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(ItsnowHost another) {
        return getLeftCapacity() - another.getLeftCapacity();
    }

    @JsonIgnore
    public int getLeftCapacity() {
        return this.getCapacity() - processesCount - schemasCount;
    }
}
