/**
 * xiongjie on 14-8-20.
 */
package dnt.itsnow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;
import sun.security.krb5.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>角色对象</h1>
 * <p/>
 */
public class Role extends ConfigItem {

    @Override
    public String toString() {
        return "Role{" +
                "id=" + super.getId() +
                ", name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                ", createdAt=" + super.getCreatedAt() +
                ", updatedAt=" + super.getUpdatedAt() +
                '}';
    }
}