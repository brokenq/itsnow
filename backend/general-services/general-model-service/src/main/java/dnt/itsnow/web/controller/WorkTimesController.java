package dnt.itsnow.web.controller;

import dnt.itsnow.exception.WorkTimeException;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <h1>WorkTime Controller</h1>
 */
@RestController
@RequestMapping("/api/work-times")
public class WorkTimesController extends SessionSupportController<WorkTime> {

    WorkTime workTime;

    @Autowired
    private WorkTimeService service;

    @RequestMapping
    public Page<WorkTime> index(@RequestParam(value = "keyword", required = false) String keyword){
        logger.debug("Listing Work Times by keyword: {}", keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed  {}", indexPage);
        return indexPage;
    }

    @RequestMapping("/{sn}")
    public WorkTime show(){
        if (workTime == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The work time sn must be specified");
        }
        return workTime;
    }

    @RequestMapping(method = RequestMethod.POST)
    public WorkTime create(@Valid @RequestBody WorkTime workTime){
        logger.info("Creating {}", workTime.getName());

        try {
            workTime = service.create(workTime);
        } catch (WorkTimeException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", workTime.getName());
        return workTime;
    }

    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public WorkTime update(@Valid @RequestBody WorkTime workTime){
        logger.info("Updateing {}", workTime.getName());

        if (workTime == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The work time sn must be specified");
        }

        this.workTime.apply(workTime);
        try {
            workTime = service.update(workTime);
        } catch (WorkTimeException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", workTime.getName());

        return workTime;
    }

    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public WorkTime destroy(){

        if (workTime == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The work time sn must be specified");
        }

        try {
            service.destroy(workTime.getSn());
        } catch (WorkTimeException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return workTime;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initWorkTime(@PathVariable("sn") String sn){
        this.workTime = service.findBySn(sn);//find it by sn
    }
    
}
