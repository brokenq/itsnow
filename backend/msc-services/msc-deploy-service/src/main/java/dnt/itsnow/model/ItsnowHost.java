/**
 * Developer: Kadvin Date: 14-8-27 下午12:17
 */
package dnt.itsnow.model;

import javax.validation.constraints.NotNull;

/**
 * The itsnow host which can service for MSC/MSU/MSP
 */
public class ItsnowHost extends DeployResource
{
    @NotNull
    private String address;
    private HostStatus status = HostStatus.Planing;
    //这台主机一般能部署多少个Itsnow Process
    private int    capacity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItsnowHost)) return false;
        if (!super.equals(o)) return false;

        ItsnowHost that = (ItsnowHost) o;

        if (capacity != that.capacity) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        //noinspection RedundantIfStatement
        if (status != that.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + status.hashCode();
        result = 31 * result + capacity;
        return result;
    }
}
