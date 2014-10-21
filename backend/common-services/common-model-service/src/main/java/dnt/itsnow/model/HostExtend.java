/**
 * Developer: Kadvin Date: 14-8-27 下午12:17
 */
package dnt.itsnow.model;

/**
 * extend fields show in list
 */
public class HostExtend
{
    private int processCount;
    private int schemaCount;

    public int getProcessCount() {
        return processCount;
    }

    public void setProcessCount(int processCount) {
        this.processCount = processCount;
    }

    public int getSchemaCount() {
        return schemaCount;
    }

    public void setSchemaCount(int schemaCount) {
        this.schemaCount = schemaCount;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + processCount;
        result = 31 * result + schemaCount;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItsnowHost)) return false;
        if (!super.equals(o)) return false;

        HostExtend that = (HostExtend) o;

        if (processCount != that.processCount) return false;
        if (schemaCount != that.schemaCount) return false;

        return true;
    }
}
