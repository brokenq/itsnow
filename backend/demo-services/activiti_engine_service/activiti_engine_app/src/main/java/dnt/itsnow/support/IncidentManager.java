package dnt.itsnow.support;

import dnt.itsnow.model.Incident;
import dnt.itsnow.repository.IncidentRepository;
import dnt.itsnow.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Beans;
import java.util.List;

/**
 * Created by jacky on 2014/7/28.
 */
@Service
public class IncidentManager extends Beans implements IncidentService {

    @Autowired
    IncidentRepository incidentRepository;

    public void newIncident(Incident incident){
        incidentRepository.save(incident);
    }

    public List<Incident> findIncidents(String orderBy, int offset, int size){
        return incidentRepository.findIncidents(orderBy,offset,size);
    }
}
