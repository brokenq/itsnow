/**
 * Developer: Kadvin Date: 14-8-27 下午12:17
 */
package dnt.itsnow.model;

import dnt.support.DefaultHostAddress;

import java.util.Properties;

/**
 * The itsnow host which can service for MSC/MSU/MSP
 */
public class ItsnowHost extends ConfigItem
{
    private DefaultHostAddress address;
    private HostStatus status = HostStatus.Planing;
    //这台主机一般能部署多少个Itsnow Process
    private Integer    capacity;
    //这台主机的配置项，一般为CPU、内存、磁盘空间大小等
    private Properties configuration;


    public DefaultHostAddress getAddress() {
        return address;
    }

    public void setAddress(DefaultHostAddress address) {
        this.address = address;
    }

    public HostStatus getStatus() {
        return status;
    }

    public void setStatus(HostStatus status) {
        this.status = status;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }
}
