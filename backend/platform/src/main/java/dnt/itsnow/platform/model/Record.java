/**
 * Developer: Kadvin Date: 14-7-18 下午2:42
 */
package dnt.itsnow.platform.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;

/**
 * <h1>一般的业务对象</h1>
 * <p/>
 * 在数据库中以主表的形式出现（而不是关联表）
 */
public class Record {
    protected static ObjectMapper mapper = new ObjectMapper();

    private Long      id;
    private Timestamp createdAt, updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt == null ? new Timestamp(System.currentTimeMillis()) : createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt == null ? new Timestamp(System.currentTimeMillis()) : updatedAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 将另外一个对象的属性设置到本对象上来
     *
     * @param another 另外一个对象
     */
    public void apply(Record another) {
        // do nothing
    }
}
