/**
 * Developer: Kadvin Date: 14-8-27 下午12:17
 */
package dnt.itsnow.model;

/**
 * extend fields show in list
 */
public class HostExtend
{
    private int processesCount;
    private int schemasCount;

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
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + processesCount;
        result = 31 * result + schemasCount;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItsnowHost)) return false;
        if (!super.equals(o)) return false;

        HostExtend that = (HostExtend) o;

        if (processesCount != that.processesCount) return false;
        if (schemasCount != that.schemasCount) return false;

        return true;
    }
}
