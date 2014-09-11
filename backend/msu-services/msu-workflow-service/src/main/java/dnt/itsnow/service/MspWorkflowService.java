package dnt.itsnow.demo.service;

import dnt.itsnow.demo.model.Incident;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * Created by jacky on 2014/7/28.
 */
public interface MspWorkflowService {

    void newIncident(Incident incident);

    Incident findByInstanceId(String id);

    Page<Incident> findByInstanceIds(List<String> ids,String keyword,Pageable pageable);

    Page<Incident> findByUsername(String username,Pageable pageable);

    List<Incident> findIncidents(String orderBy, int offset, int size);

    Page<Incident> findAll(String keyword, Pageable pageable);

    void updateIncident(Incident mspIncident);

    void closeIncident(Incident mspIncident);
}
