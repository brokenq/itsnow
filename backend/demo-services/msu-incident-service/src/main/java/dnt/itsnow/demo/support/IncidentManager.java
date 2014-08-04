/**
 * Created by jacky on 2014/7/28.
 */
package dnt.itsnow.demo.support;

import dnt.itsnow.demo.model.Incident;
import dnt.itsnow.demo.repository.IncidentRepository;
import dnt.itsnow.demo.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Beans;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class IncidentManager extends Beans implements IncidentService {

    @Autowired
    IncidentRepository incidentRepository;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    public void newIncident(Incident incident){
        incident.setNumber("INC"+df.format(new Date()));
        incidentRepository.save(incident);
    }

    public List<Incident> findIncidents(String orderBy, int offset, int size){

        return incidentRepository.findIncidents(orderBy,offset,size);
    }

    @Override
    public void updateIncident(Incident incident) {
        incidentRepository.update(incident);
    }

    @Override
    public void closeIncident(Incident incident) {
        incidentRepository.close(incident);
    }
}
