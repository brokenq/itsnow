package dnt.itsnow.demo.service;

import dnt.itsnow.demo.model.MsuIncident;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * Created by jacky on 2014/7/28.
 */
public interface IncidentService {

    void newIncident(MsuIncident incident);

    MsuIncident findByInstanceId(String id);

    Page<MsuIncident> findByInstanceIds(List<String> ids, String keyword, Pageable pageable);

    Page<MsuIncident> findByUsername(String username, Pageable pageable);

    List<MsuIncident> findIncidents(String orderBy, int offset, int size);

    Page<MsuIncident> findAll(String keyword, Pageable pageable);

    void updateIncident(MsuIncident incident);

    void closeIncident(MsuIncident incident);
}
