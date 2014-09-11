package dnt.itsnow.service;

import dnt.itsnow.exception.WorkTimeException;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>工作时间Service</h1>
 */
public interface WorkTimeService {

    public Page<WorkTime> findAll(String keyword, Pageable pageable);

    public WorkTime findBySn(String sn);

    public WorkTime create(WorkTime workTime) throws WorkTimeException;

    public WorkTime update(WorkTime workTime) throws WorkTimeException;

    public void destroy(String sn) throws WorkTimeException;

}
