/**
 * Developer: Kadvin Date: 14-9-15 下午1:27
 */
package dnt.itsnow.model;

import java.sql.Timestamp;

/**
 * <h1>对应于Record的Client Json解析对象</h1>
 */
public class ClientRecord {
    private Long      id;
    private Timestamp createdAt, updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
