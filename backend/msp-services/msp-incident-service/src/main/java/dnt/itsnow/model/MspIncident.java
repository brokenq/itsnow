package dnt.itsnow.model;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.task.Task;

import java.util.ArrayList;
import java.util.List;

public class MspIncident {

    Incident incident;

    List<MspIncidentTask> tasksList;

    List<HistoricActivityInstance> historicActivityInstanceList;

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

    public List<MspIncidentTask> getTasksList() {
        return tasksList;
    }

    public void setTasksList(List<MspIncidentTask> tasksList) {
        this.tasksList = tasksList;
    }

    public void setTasks(List<Task> list){
        if(this.tasksList == null)
            this.tasksList = new ArrayList<MspIncidentTask>();
        for(Task task:list){
            MspIncidentTask t = new MspIncidentTask();
            t.setTaskId(task.getId());
            t.setTaskAssignee(task.getAssignee());
            t.setTaskDescription(task.getDescription());
            t.setTaskName(task.getName());
            tasksList.add(t);
        }
    }
}
