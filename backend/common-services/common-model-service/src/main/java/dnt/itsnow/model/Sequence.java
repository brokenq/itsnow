/**
 * Developer: Kadvin Date: 14/11/4 下午8:51
 */
package dnt.itsnow.model;

/**
 * The DB Sequence
 */
public class Sequence {
    private String catalog;
    private String rule;
    private int    increment;
    private long value;

    public Sequence() {
    }

    public Sequence(String catalog, String rule) {
        this.catalog = catalog;
        this.rule = rule;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
