package dnt.itsnow.support;

import dnt.itsnow.exception.WorkTimeException;
import dnt.itsnow.model.WorkTime;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.service.Pageable;
import net.happyonroad.platform.util.DefaultPage;
import dnt.itsnow.repository.WorkTimeRepository;
import dnt.itsnow.service.WorkTimeService;
import net.happyonroad.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>工作时间业务实现类</h1>
 */
@Service
public class WorkTimeManager extends Bean implements WorkTimeService {

    @Autowired
    private WorkTimeRepository repository;

    @Override
    public Page<WorkTime> findAll(String keyword, Pageable pageable) {

        logger.debug("Finding work time by keyword: {}", keyword);

        int total = repository.count(keyword);
        List<WorkTime> dictionaries = new ArrayList<WorkTime>();
        if (total > 0) {
            dictionaries = repository.findAll(keyword, pageable);
        }
        DefaultPage<WorkTime> page = new DefaultPage<WorkTime>(dictionaries, pageable, total);

        logger.debug("Found   {}", page);

        return page;
    }

    @Override
    public WorkTime findBySn(String sn) {

        logger.debug("Finding {}", sn);

        WorkTime workTime = repository.findBySn(sn);

        logger.debug("Found   {}", sn);

        return workTime;
    }

    @Override
    public WorkTime create(WorkTime workTime) throws WorkTimeException {

        logger.info("Creating {}", workTime);

        if (workTime == null) {
            throw new WorkTimeException("work time entry can not be empty");
        }
        workTime.creating();
        workTime.setSn(UUID.randomUUID().toString().substring(0,8));
       // workTime.setName("xxx");
        workTime.setWorkDays("undefine");
        repository.create(workTime);

        logger.info("Created  {}", workTime);

        return workTime;
    }

    @Override
    public WorkTime update(WorkTime workTime) throws WorkTimeException {

        logger.info("Updating {}", workTime);

        if (workTime == null) {
            throw new WorkTimeException("Work time entry can not be empty");
        }
        workTime.updating();
        repository.update(workTime);

        logger.info("Updated  {}", workTime);

        return workTime;
    }

    @Override
    public void destroy(String sn) throws WorkTimeException {

        logger.warn("Deleting work time by sn: {}", sn);

        if (StringUtils.isBlank(sn)) {
            throw new WorkTimeException("Name of work time can not be empty");
        }
        repository.delete(sn);

        logger.warn("Deleted  work time by sn: {}", sn);
    }

}
