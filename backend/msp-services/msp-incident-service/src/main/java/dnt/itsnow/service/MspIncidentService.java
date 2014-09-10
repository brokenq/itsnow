package dnt.itsnow.service;

import dnt.itsnow.model.Incident;
import dnt.itsnow.model.MspIncident;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * Created by jacky on 2014/7/28.
 */
public interface MspIncidentService {

    Page<Incident> findByUserAndKey(String username, String keyword, Pageable pageable);

    Page<Incident> findClosedByUserAndKey(String username, String keyword, Pageable pageable);

    MspIncident findByInstanceId(String instanceId,boolean withHistory);

    MspIncident startIncident(String accountName,String username,Incident incident);

    MspIncident processIncident(String instanceId,String taskId,String username,Incident incident);
}
