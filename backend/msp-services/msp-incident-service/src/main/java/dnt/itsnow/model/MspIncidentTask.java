package dnt.itsnow.model;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by jacky on 2014/9/10.
 */
public class MspIncidentTask {

    @NotBlank
    String taskId;
    @NotBlank
    String taskName;
    @NotBlank
    String taskDescription;
    String taskAssignee;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskAssignee() {
        return taskAssignee;
    }

    public void setTaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MspIncidentTask that = (MspIncidentTask) o;

        if (!taskId.equals(that.taskId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taskId != null ? taskId.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (taskDescription != null ? taskDescription.hashCode() : 0);
        result = 31 * result + (taskAssignee != null ? taskAssignee.hashCode() : 0);
        return result;
    }

}
