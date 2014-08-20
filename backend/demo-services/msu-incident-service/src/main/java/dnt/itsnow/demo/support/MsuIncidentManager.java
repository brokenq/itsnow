/**
 * Created by jacky on 2014/7/28.
 */
package dnt.itsnow.demo.support;

import dnt.itsnow.api.ActivitiEngineService;
import dnt.itsnow.demo.model.Incident;
import dnt.itsnow.demo.model.IncidentStatus;
import dnt.itsnow.demo.repository.MsuIncidentRepository;
import dnt.itsnow.demo.service.IncidentService;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.messaging.MessageBus;
import dnt.spring.Bean;
import dnt.util.StringUtils;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class MsuIncidentManager extends Bean implements IncidentService {

    @Autowired
    MsuIncidentRepository msuIncidentRepository;

    @Autowired
    ActivitiEngineService activitiEngineService;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Autowired
    MsuEventListener msuEventListener;

    @Autowired
    MessageBus messageBus;

    public static final String PROCESS_KEY = "msu_incident";

    private static final String LISTENER = "listener";

    private static final String LISTEN_CHANNEL = "MSP-001-TO-MSU-001";

    @Override
    protected void performStart() {
        this.autoDeployment();
        activitiEngineService.addEventListener(msuEventListener, ActivitiEventType.ACTIVITY_COMPLETED);
        //add message-bus listener
        messageBus.subscribe(LISTENER,LISTEN_CHANNEL,msuEventListener);
    }

    @Override
    protected void performStop() {
        messageBus.unsubscribe(LISTENER);
    }

    private void autoDeployment() {
        String path = "bpmn/"+PROCESS_KEY+".bpmn20.xml";
        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            assert url != null;
            InputStream is = url.openStream();
            activitiEngineService.deploySingleProcess(is,PROCESS_KEY,PROCESS_KEY);
            is.close();
        }catch(Exception e){
            logger.warn("error:{}",e);
        }
    }

    public void newIncident(Incident incident){
        incident.setNumber("INC"+df.format(new Date()));
        incident.setMsuStatus(IncidentStatus.Assigned);
        msuIncidentRepository.save(incident);
    }

    public Page<Incident> findByUsername(String username,Pageable pageable){
        long total = msuIncidentRepository.countByUsername(username);
        List<Incident> incidents = msuIncidentRepository.findIncidentsByUsername(username,"updated_at desc",pageable.getOffset(),pageable.getPageNumber());
        return new DefaultPage<Incident>(incidents, pageable, total);
    }

    public Incident findByInstanceId(String id){
        return msuIncidentRepository.findByInstanceId(id);
    }

    public Page<Incident> findByInstanceIds(List<String> ids,String keyword,Pageable pageable){

        int total = ids.size();
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<Incident> incidents = msuIncidentRepository.findAllByInstanceIds(ids,keyword,pageable);
            return new DefaultPage<Incident>(incidents,pageable,total);
        }else {
            List<Incident> incidents = new ArrayList<Incident>();
            return new DefaultPage<Incident>(incidents, pageable, total);
        }
    }

    public Page<Incident> findAll(String keyword, Pageable pageable){
        if(StringUtils.isBlank(keyword)){
            int total = msuIncidentRepository.count();
            List<Incident> incidents = msuIncidentRepository.findIncidents("updated_at desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Incident>(incidents, pageable, total);
        }else{
            int total = msuIncidentRepository.countByKeyword(keyword);
            List<Incident> incidents = msuIncidentRepository.findIncidentsByKeyword(keyword, "updated_at desc",
                    pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Incident>(incidents, pageable, total);
        }
    }

    public List<Incident> findIncidents(String orderBy, int offset, int size){

        return msuIncidentRepository.findIncidents(orderBy,offset,size);
    }

    @Override
    public void updateIncident(Incident incident) {
        msuIncidentRepository.update(incident);
    }

    @Override
    public void closeIncident(Incident incident) {
        msuIncidentRepository.close(incident);
    }
}
