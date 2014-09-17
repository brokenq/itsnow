package dnt.itsnow.support;

import dnt.itsnow.exception.WorkTimeException;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.WorkTimeRepository;
import dnt.itsnow.service.WorkTimeService;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>工作时间服务实现类</h1>
 */
@Service
public class WorkTimeManager extends Bean implements WorkTimeService {

    @Autowired
    private WorkTimeRepository repository;

    @Override
    public Page<WorkTime> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding work time by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = repository.count();
            List<WorkTime> dictionaries = repository.find("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<WorkTime>(dictionaries, pageable, total);
        }else{
            int total = repository.countByKeyword("%"+keyword+"%");
            List<WorkTime> dictionaries = repository.findByKeyword("%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<WorkTime>(dictionaries, pageable, total);
        }
    }

    @Override
    public WorkTime findBySn(String sn) {
        logger.debug("Finding work time by sn: {}", sn);

        return repository.findBySn(sn);
    }

    @Override
    public WorkTime create(WorkTime workTime) throws WorkTimeException {
        logger.info("Creating work time {}", workTime);
        if(workTime == null){
            throw new WorkTimeException("work time entry can not be empty");
        }
        workTime.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        workTime.setUpdatedAt(workTime.getCreatedAt());
        repository.create(workTime);
        return workTime;
    }

    @Override
    public WorkTime update(WorkTime workTime) throws WorkTimeException {
        logger.info("Updating work time {}", workTime);
        if(workTime==null){
            throw new WorkTimeException("Work time entry can not be empty");
        }
        repository.update(workTime);
        return workTime;
    }

    @Override
    public void destroy(String sn) throws WorkTimeException {
        logger.warn("Deleting work time sn: {}", sn);
        if(StringUtils.isBlank(sn)){
            throw new WorkTimeException("Name of work time can not be empty");
        }
        repository.delete(sn);
    }

}
