/**
 * Created by jacky on 2014/7/28.
 */
package dnt.itsnow.demo.support;

import dnt.itsnow.demo.model.Incident;
import dnt.itsnow.demo.repository.IncidentRepository;
import dnt.itsnow.demo.service.IncidentService;
import dnt.itsnow.platform.service.DefaultPage;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Beans;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service
public class IncidentManager extends Beans implements IncidentService {

    @Autowired
    IncidentRepository incidentRepository;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public void newIncident(Incident incident){
        incident.setNumber("INC"+df.format(new Date()));
        incidentRepository.save(incident);
    }

    public Page<Incident> findByUsername(String username,Pageable pageable){
        long total = incidentRepository.countByUsername(username);
        List<Incident> incidents = incidentRepository.findIncidentsByUsername(username,"updated_at desc",pageable.getOffset(),pageable.getPageNumber());
        return new DefaultPage<Incident>(incidents, pageable, total);
    }

    public Page<Incident> findByInstanceIds(Set<String> ids,Pageable pageable){
        int total = ids.size();
        List<Incident> incidents = incidentRepository.findIncidentsByIds(ids,"updated_at desc",pageable.getOffset(),pageable.getPageSize());
        return new DefaultPage<Incident>(incidents,pageable,total);
    }

    public Page<Incident> findAll(String keyword, Pageable pageable){
        if(StringUtils.isBlank(keyword)){
            int total = incidentRepository.count();
            List<Incident> incidents = incidentRepository.findIncidents("updated_at desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Incident>(incidents, pageable, total);
        }else{
            int total = incidentRepository.countByKeyword(keyword);
            List<Incident> incidents = incidentRepository.findIncidentsByKeyword(keyword, "updated_at desc",
                    pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Incident>(incidents, pageable, total);
        }
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
