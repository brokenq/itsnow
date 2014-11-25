package dnt.itsnow.model;

/**
 * Created by user on 2014/11/18.
 */
public class DictDetail {
    private String key;
    private String value;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DictDetail{");
        sb.append("key='").append(key).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
