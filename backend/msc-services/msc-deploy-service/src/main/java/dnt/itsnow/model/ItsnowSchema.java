/**
 * Developer: Kadvin Date: 14-8-27 下午1:21
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

/**
 * Itsnow DB Schema
 */
public class ItsnowSchema extends DeployResource {
    @NotNull
    private Long hostId;
    @JsonIgnore
    private ItsnowHost host;

    @NotNull
    @Override
    public String getName() {
        return super.getName();
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public ItsnowHost getHost() {
        return host;
    }

    public void setHost(ItsnowHost host) {
        this.host = host;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItsnowSchema)) return false;
        if (!super.equals(o)) return false;

        ItsnowSchema that = (ItsnowSchema) o;

        //noinspection RedundantIfStatement
        if (hostId != null ? !hostId.equals(that.hostId) : that.hostId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (hostId != null ? hostId.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    public String getHostAddress(){
        if( host != null ) return host.getAddress();
        return null;
    }

}
