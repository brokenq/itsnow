package dnt.itsnow.service;

import dnt.itsnow.model.Incident;

import java.util.List;

/**
 * Created by jacky on 2014/7/28.
 */
public interface IncidentService {

    void newIncident(Incident incident);

    List<Incident> findIncidents(String orderBy, int offset, int size);
}
