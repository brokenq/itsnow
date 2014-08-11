/**
 * Created by jacky on 2014/7/28.
 */
package dnt.itsnow.demo.support;

import dnt.itsnow.demo.model.MspIncident;
import dnt.itsnow.demo.repository.MspIncidentRepository;
import dnt.itsnow.demo.service.MspIncidentService;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.spring.Bean;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class MspIncidentManager extends Bean implements MspIncidentService {

    @Autowired
    MspIncidentRepository mspIncidentRepository;

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public void newIncident(MspIncident incident){
        incident.setNumber("INC"+df.format(new Date()));
        mspIncidentRepository.save(incident);
    }

    public Page<MspIncident> findByUsername(String username,Pageable pageable){
        long total = mspIncidentRepository.countByUsername(username);
        List<MspIncident> incidents = mspIncidentRepository.findIncidentsByUsername(username,"updated_at desc",pageable.getOffset(),pageable.getPageNumber());
        return new DefaultPage<MspIncident>(incidents, pageable, total);
    }

    public MspIncident findByInstanceId(String id){
        return mspIncidentRepository.findByInstanceId(id);
    }

    public Page<MspIncident> findByInstanceIds(List<String> ids,String keyword,Pageable pageable){

        int total = ids.size();
        if(total > 0){
            if(keyword == null)
                keyword = "";
            List<MspIncident> incidents = mspIncidentRepository.findAllByInstanceIds(ids,keyword,pageable);
            return new DefaultPage<MspIncident>(incidents,pageable,total);
        }else {
            List<MspIncident> incidents = new ArrayList<MspIncident>();
            return new DefaultPage<MspIncident>(incidents, pageable, total);
        }
    }

    public Page<MspIncident> findAll(String keyword, Pageable pageable){
        if(StringUtils.isBlank(keyword)){
            int total = mspIncidentRepository.count();
            List<MspIncident> incidents = mspIncidentRepository.findIncidents("updated_at desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<MspIncident>(incidents, pageable, total);
        }else{
            int total = mspIncidentRepository.countByKeyword(keyword);
            List<MspIncident> incidents = mspIncidentRepository.findIncidentsByKeyword(keyword, "updated_at desc",
                    pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<MspIncident>(incidents, pageable, total);
        }
    }

    public List<MspIncident> findIncidents(String orderBy, int offset, int size){

        return mspIncidentRepository.findIncidents(orderBy,offset,size);
    }

    @Override
    public void updateIncident(MspIncident incident) {
        mspIncidentRepository.update(incident);
    }

    @Override
    public void closeIncident(MspIncident incident) {
        mspIncidentRepository.close(incident);
    }
}
