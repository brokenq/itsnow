package dnt.itsnow.model;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.task.Task;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>MsuIncident实体类</h1>
 */
public class MsuIncident {

    @NotNull
    Incident incident;

    List<MsuIncidentTask> tasksList;

    List<HistoricActivityInstance> historicActivityInstanceList;

    @NotNull
    String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    public List<HistoricActivityInstance> getHistoricActivityInstanceList() {
        return historicActivityInstanceList;
    }

    public void setHistoricActivityInstanceList(List<HistoricActivityInstance> historicActivityInstanceList) {
        this.historicActivityInstanceList = historicActivityInstanceList;
    }

    public List<MsuIncidentTask> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<MsuIncidentTask> tasksList) {
        this.tasksList = tasksList;
    }

    public void setTasks(List<Task> list){
        if(this.tasksList == null)
            this.tasksList = new ArrayList<MsuIncidentTask>();
        for(Task task:list){
            MsuIncidentTask t = new MsuIncidentTask();
            t.setTaskId(task.getId());
            t.setTaskAssignee(task.getAssignee());
            t.setTaskDescription(task.getDescription());
            t.setTaskName(task.getName());
            tasksList.add(t);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MsuIncident that = (MsuIncident) o;

        if (!incident.equals(that.incident)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = incident != null ? incident.hashCode() : 0;
        result = 31 * result + (tasksList != null ? tasksList.hashCode() : 0);
        result = 31 * result + (historicActivityInstanceList != null ? historicActivityInstanceList.hashCode() : 0);
        return result;
    }
}
