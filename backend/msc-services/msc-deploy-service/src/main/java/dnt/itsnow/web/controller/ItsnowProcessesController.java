/**
 * Developer: Kadvin Date: 14-9-15 上午10:48
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.ItsnowProcessException;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.ItsnowProcessService;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Itsnow processes web controller</h1>
 * <pre>
 * <b>HTTP     URI                                  方法       含义  </b>
 * GET    /admin/api/processes                     index     列出所有进程，支持过滤，分页，排序等
 * POST   /admin/api/processes                     create    为特定帐户分配(创建)进程
 * GET    /admin/api/processes/{name}              show      查看特定进程的信息
 * PUT    /admin/api/processes/{name}/start        start     启动进程
 * PUT    /admin/api/processes/{name}/stop         stop      停止进程
 * PUT    /admin/api/processes/{name}/cancel       cancel    取消最近的操作(启动/停止)
 * DELETE /admin/api/processes/{name}              destroy   删除进程对象
 * GET    /admin/api/processes/{name}/follow/{job} follow    获取特定进程最新的任务信息
 *
 * </pre>
 */
@RestController
@RequestMapping("/admin/api/processes")
public class ItsnowProcessesController extends SessionSupportController<ItsnowProcess> {
    @Autowired
    ItsnowProcessService processService;
    //current operating process shared between filter and controller method
    ItsnowProcess currentProcess;

    /**
     * <h2>查看所有的服务进程</h2>
     * <p/>
     * GET /admin/api/processes?page={int}&count={int}&sort={string}&keyword={string}
     *
     * @param keyword 进程名称/描述中的关键词
     * @return 进程列表
     */
    @RequestMapping
    public List<ItsnowProcess> index( @RequestParam(value = "keyword", required = false) String keyword ) {
        logger.debug("Listing itsnow processes");
        indexPage = processService.findAll(keyword, pageRequest);
        logger.debug("Found   itsnow processes {}", indexPage.getTotalElements());
        return indexPage.getContent();
    }

    /**
     * <h2>查看特定服务进程</h2>
     * <p/>
     * GET /admin/api/processes/{name}
     */
    @RequestMapping("{name}")
    public ItsnowProcess show() {
        logger.debug("Viewed  {}", currentProcess);
        return currentProcess;
    }

    /**
     * <h2>创建(分配)服务进程</h2>
     * <p/>
     * POST /admin/api/processes
     *
     * @param creating 通过http post body以JSON等方式提交的数据
     * @return 刚刚创建的进程对象信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public ItsnowProcess create(@Valid @RequestBody ItsnowProcess creating){
        logger.info("Creating {} Itsnow Process {}", creating);
        try{
            // 可能会抛出重名的异常(重名由数据库uk保证)
            currentProcess= processService.create(creating);
            logger.info("Created  {} Itsnow Process {} ", currentProcess);
            return currentProcess;
        }catch (ItsnowProcessException ex){
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * <h2>删除特定服务进程</h2>
     * <p/>
     * DELETE /admin/api/processes/{name}
     */
    @RequestMapping("{name}")
    public void destroy() {
        logger.debug("Destroying {}", currentProcess);
        try {
            processService.delete(currentProcess);
        } catch (ItsnowProcessException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    /**
     * <h2>启动特定进程</h2>
     * <p/>
     * PUT /admin/api/processes/{name}/start
     * Response: 启动进程的任务描述符
     */
    @RequestMapping("{name}/start")
    public String start(){
        logger.info("Starting {}", currentProcess.getName());
        try {
            String job = processService.start(currentProcess);
            logger.info("Starting process with job {}", job);
            return job;
        } catch (ItsnowProcessException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    /**
     * <h2>停止特定进程</h2>
     * <p/>
     * PUT /admin/api/processes/{name}/stop
     * Response: 停止进程的任务描述符
     */
    @RequestMapping("{name}/stop")
    public String stop(){
        logger.info("Stopping {}", currentProcess.getName());
        try {
            String job = processService.stop(currentProcess);
            logger.info("Stopping process with job {}", job);
            return job;
        } catch (ItsnowProcessException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }


    /**
     * <h2>取消启动/停止特定进程</h2>
     * <p/>
     * PUT /admin/api/processes/{name}/cancel/{job}
     */
    @RequestMapping("{name}/cancel/{job}")
    public void cancel(@PathVariable("job") String job){
        logger.info("Cancel {}'s job {}", currentProcess.getName(), job);
        try {
            processService.cancel(currentProcess, job);
        } catch (ItsnowProcessException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    /**
     * <h2>查看特定任务的最新信息</h2>
     * <p/>
     * PUT /admin/api/processes/{name}/follow/{job}
     * <pre>
     * Response body:
     *   返回从offset行开始的任务的输出信息，如果offset值不正确，则以错误响应
     * Response Header:
     *   offset: 下一次来获取信息应该传入的offset值
     * </pre>
     */
    @RequestMapping("{name}/follow/{job}")
    public String follow( @PathVariable("job") String job,
                          @RequestParam("offset") int offset,
                          HttpServletResponse response){
        logger.debug("Follow {} job {} from {}", currentProcess.getName(), job, offset);
        String[] body = processService.follow(currentProcess, job, offset);
        response.setHeader("offset", String.valueOf(offset + body.length));
        return StringUtils.join(body, "\n");
    }


    @BeforeFilter({"show", "start", "stop", "cancel", "destroy", "follow"})
    public void initCurrentProcess(@PathVariable("name") String name){
        logger.debug("Finding itsnow process {}", name);
        currentProcess = processService.findByName(name);
        if( currentProcess == null )
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the itsnow process with name = " + name);
    }


}