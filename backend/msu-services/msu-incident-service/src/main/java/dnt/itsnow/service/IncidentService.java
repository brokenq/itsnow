package dnt.itsnow.service;

import dnt.itsnow.model.Incident;
import dnt.itsnow.model.MsuIncident;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * Created by jacky on 2014/7/28.
 */
public interface IncidentService {

    Page<Incident> findByUserAndKey(String username, String keyword, Pageable pageable);

    Page<Incident> findClosedByUserAndKey(String username, String keyword, Pageable pageable);

    MsuIncident findByInstanceId(String instanceId,boolean withHistory);

    MsuIncident startIncident(String accountName,String username,Incident incident);

    MsuIncident processIncident(String instanceId,String taskId,String username,Incident incident);
}
