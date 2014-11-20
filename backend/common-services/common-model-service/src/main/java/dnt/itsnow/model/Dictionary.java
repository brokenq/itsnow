/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Arrays;
import java.util.List;

/**
 * <h1>流程字典对象</h1>
 * <p/>
 */
public class Dictionary extends ConfigItem {

    @NotBlank
    private String code;
    @NotBlank
    private String label;

    private DictDetail[] details;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public DictDetail[] getDetails() {
        return details;
    }

    public void setDetails(DictDetail[] details) {
        this.details = details;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dictionary{");
        sb.append("code='").append(code).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", details=").append(Arrays.toString(details));
        sb.append('}');
        return sb.toString();
    }
}
