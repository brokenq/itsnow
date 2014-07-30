package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;

/**
 * Created by jacky on 2014/7/28.
 */
public class Incident extends Record {

    private String topic;//主题
    private String solution1;
    private String solution2;
    private String resolved;
    private String closeCode;
    private String instanceId;
    private String owner;
    private String status;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSolution1() {
        return solution1;
    }

    public void setSolution1(String solution1) {
        this.solution1 = solution1;
    }

    public String getSolution2() {
        return solution2;
    }

    public void setSolution2(String solution2) {
        this.solution2 = solution2;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getCloseCode() {
        return closeCode;
    }

    public void setCloseCode(String closeCode) {
        this.closeCode = closeCode;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
