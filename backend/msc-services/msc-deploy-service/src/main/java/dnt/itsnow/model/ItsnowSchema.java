/**
 * Developer: Kadvin Date: 14-8-27 下午1:21
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.Properties;

/**
 * Itsnow DB Schema
 */
public class ItsnowSchema extends ConfigItem {
    @NotNull
    private Integer hostId;
    @JsonIgnore
    private ItsnowHost host;
    private Properties configuration;

    @NotNull
    @Override
    public String getName() {
        return super.getName();
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public ItsnowHost getHost() {
        return host;
    }

    public void setHost(ItsnowHost host) {
        this.host = host;
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItsnowSchema)) return false;
        if (!super.equals(o)) return false;

        ItsnowSchema that = (ItsnowSchema) o;

        if (configuration != null ? !configuration.equals(that.configuration) : that.configuration != null)
            return false;
        if (hostId != null ? !hostId.equals(that.hostId) : that.hostId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (hostId != null ? hostId.hashCode() : 0);
        result = 31 * result + (configuration != null ? configuration.hashCode() : 0);
        return result;
    }

    public SystemJob createJob() {
        return new SystemJob(getHost(), null, "create_schema.sh " + getName());
    }

    public SystemJob dropJob() {
        return new SystemJob(getHost(), null, "drop_schema.sh " + getName());
    }
}
