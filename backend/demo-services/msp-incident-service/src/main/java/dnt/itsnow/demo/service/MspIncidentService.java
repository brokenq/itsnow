package dnt.itsnow.demo.service;

import dnt.itsnow.demo.model.MspIncident;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * Created by jacky on 2014/7/28.
 */
public interface MspIncidentService {

    void newIncident(MspIncident incident);

    MspIncident findByInstanceId(String id);

    Page<MspIncident> findByInstanceIds(List<String> ids,String keyword,Pageable pageable);

    Page<MspIncident> findByUsername(String username,Pageable pageable);

    List<MspIncident> findIncidents(String orderBy, int offset, int size);

    Page<MspIncident> findAll(String keyword, Pageable pageable);

    void updateIncident(MspIncident mspIncident);

    void closeIncident(MspIncident mspIncident);
}
