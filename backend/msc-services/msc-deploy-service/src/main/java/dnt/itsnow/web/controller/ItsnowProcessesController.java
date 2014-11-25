/**
 * Developer: Kadvin Date: 14-9-15 上午10:48
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.ItsnowProcessException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.ItsnowHostService;
import dnt.itsnow.service.ItsnowProcessService;
import dnt.itsnow.service.ItsnowSchemaService;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h1>Itsnow processes web controller</h1>
 * <pre>
 * <b>HTTP     URI                                              方法             含义  </b>
 * GET    /admin/api/processes                                  index           列出所有进程，支持过滤，分页，排序等
 * GET    /admin/api/processes/auto_new/{accountSn}             autoNew         手工分配时，为特定帐户自动的进程信息
 * POST   /admin/api/processes/auto_create/{accountSn}          autoCreate      自动分配，为特定帐户自动分配一个进程
 * POST   /admin/api/processes                                  create          为特定帐户分配(创建)进程
 * GET    /admin/api/processes/{name}                           show            查看特定进程的信息
 * PUT    /admin/api/processes/{name}/start                     start           启动进程
 * PUT    /admin/api/processes/{name}/stop                      stop            停止进程
 * PUT    /admin/api/processes/{name}/cancel                    cancel          取消最近的操作(启动/停止)
 * DELETE /admin/api/processes/{name}                           destroy         删除进程对象
 * GET    /admin/api/processes/{name}/follow/{job}              follow          获取特定进程最新的任务信息
 * GET    /admin/api/processes/{name}/wait_finished?job=job     waitFinished    等待进程Job完成
 * </pre>
 */
@RestController
@RequestMapping("/admin/api/processes")
public class ItsnowProcessesController extends SessionSupportController<ItsnowProcess> {
    static String template = "Can't find the %s id = %d";
    @Autowired
    ItsnowProcessService processService;
    @Autowired
    CommonAccountService accountService;
    @Autowired
    ItsnowHostService    hostService;
    @Autowired
    ItsnowSchemaService  schemaService;

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
    public Page<ItsnowProcess> index(@RequestParam(value = "keyword", required = false) String keyword) {
        logger.debug("Listing itsnow processes by keyword: {}", keyword);
        indexPage = processService.findAll(keyword, pageRequest);
        logger.debug("Found   {}", indexPage);
        return indexPage;
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
     * POST /admin/api/processes
     * <p/>
     *
     * 输入的数据如下:
     * <pre>
     * {
     *   name: "itsnow_baidu"
     *   accountId: "6"
     *   hostId: "15"
     *   schema{
     *     name: "itsnow_baidu"
     *     hostId: "15"
     *     configuration: {user:baidu, password:random, port:3306}
     *     description: "the db description"
     *   }
     *   configuration: {
     *     "rmi.port": "1234", "jmx.port": "2345", ...
     *   }
     *   wd: "/opt/itsnow/itsnow_baidu"
     *   description: "the host description"
     * }
     * </pre>
     * @param creating 通过http post body以JSON等方式提交的数据
     * @return 刚刚创建的进程对象信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public ItsnowProcess create(@Valid @RequestBody ItsnowProcess creating) {
        logger.info("Creating Itsnow Process {}", creating);
        try {
            // 将输入的id引用转换为已有的对象
            Account account = findAccount(creating.getAccountId());
            creating.setAccount(account);
            ItsnowHost host = findHost(creating.getHostId());
            creating.setHost(host);
            Long schemaId = creating.getSchemaId();
            if( schemaId != null && schemaId > 0 ){
                findSchema(schemaId);
            }else{
                ItsnowSchema schema = creating.getSchema();
                if(schema.getHostId().equals(host.getId())){
                    schema.setHost(host);
                }else{
                    ItsnowHost schemaHost = findHost(schema.getHostId());
                    schema.setHost(schemaHost);
                }
            }

            // 可能会抛出重名的异常(重名由数据库uk保证)
            currentProcess= processService.create(creating);
            logger.info("Created  Itsnow Process {} ", currentProcess);
            return currentProcess;
        }catch (ItsnowProcessException ex){
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    /**
        * <h2>创建(分配)服务进程</h2>
        * POST /admin/api/processes
        * <p/>
     */
   @RequestMapping(method = RequestMethod.POST, value = "auto_create/{accountSn}")
    public ItsnowProcess autoCreate(@PathVariable("accountSn") String accountSn){
       logger.info("Auto creating Itsnow Process for account with sn {}", accountSn);
       Account account = findAccount(accountSn);
       ItsnowProcess process;
       try {
           process = processService.autoCreate(account);
           logger.info("Auto created  Itsnow Process {} for account with sn {}", process, account);
       } catch (ItsnowProcessException e) {
           throw new WebServerSideException(HttpStatus.INTERNAL_SERVER_ERROR,
                                            "Can't auto create process for " + account +
                                            ", because of: " + e.getMessage());
       }
       return process;
    }

    @RequestMapping("auto_new/{accountSn}")
    public ItsnowProcess autoNew(@PathVariable("accountSn") String accountSn){
        logger.info("Suggesting Itsnow Process for account with sn {}", accountSn);
        Account account = findAccount(accountSn);
        ItsnowProcess process;
        try {
            process = processService.autoNew(account);
            logger.info("Suggested  Itsnow Process {} for account with sn {}", process, account);
        } catch (ItsnowProcessException e) {
            throw new WebServerSideException(HttpStatus.INTERNAL_SERVER_ERROR,
                                             "Can't auto new process for " + account +
                                             ", because of: " + e.getMessage());
        }
        return process;
    }
    /**
     * <h2>删除特定服务进程</h2>
     * <p/>
     * DELETE /admin/api/processes/{name}
     */
    @RequestMapping(value = "{name}", method = RequestMethod.DELETE)
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
    @RequestMapping(value = "{name}/start", method = RequestMethod.PUT)
    public Map<String, String> start(){
        logger.info("Starting {}", currentProcess.getName());
        Map<String, String> map = new HashMap<String, String>();
        try {
            String job = processService.start(currentProcess);
            logger.info("Starting process with job {}", job);
            map.put("job", job);
            return map;
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
    @RequestMapping(value = "{name}/stop", method = RequestMethod.PUT)
    public Map<String, String> stop(){
        logger.info("Stopping {}", currentProcess.getName());
        Map<String, String> map = new HashMap<String, String>();
        try {
            String job = processService.stop(currentProcess);
            logger.info("Stopping process with job {}", job);
            map.put("job", job);
            return map;
        } catch (ItsnowProcessException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }


    /**
     * <h2>取消启动/停止特定进程</h2>
     * <p/>
     * PUT /admin/api/processes/{name}/cancel/{job}
     */
    @RequestMapping(value = "{name}/cancel/{job}", method = RequestMethod.PUT)
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
    public Map<String, String> follow(@PathVariable("job") String job,
                                      @RequestParam("offset") long offset,
                                      HttpServletResponse response){
        logger.debug("Follow {} job {} from {}", currentProcess.getName(), job, offset);
        List<String> body = new LinkedList<String>();
        offset = processService.follow(currentProcess, job, offset, body);
        response.setHeader("offset", String.valueOf(offset));
        response.setHeader("status", currentProcess.getStatus().toString());
        Map<String, String> map = new HashMap<String, String>();
        map.put("logs", StringUtils.join(body, "\n"));
        return map;
    }

    /**
     * <h2>等待进程Job完成</h2>
     * <p/>
     * GET /admin/api/processes/{name}/wait_finished/{job}
     * @param name 进程名称
     * @param job 进程job
     */
    @RequestMapping("{name}/wait_finished/{job}")
    public ItsnowProcess waitFinished(@PathVariable("name") String name, @PathVariable("job") String job){
        logger.debug("Waiting for process job finished: name = {}, job = {}", name, job);
        try {
            currentProcess = processService.waitFinished(name, job);
            logger.debug("Process job finished: name = {}, job = {}", name, job);
            return currentProcess;
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @BeforeFilter({"show", "start", "stop", "cancel", "destroy", "follow"})
    public void initCurrentProcess(@PathVariable("name") String name){
        logger.debug("Finding itsnow process {}", name);
        currentProcess = processService.findByName(name);
        if( currentProcess == null )
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the itsnow process with name = " + name);
    }


    protected Account findAccount(long accountId) {
        Account account = accountService.findById(accountId);
        if( account == null )
            throw new WebClientSideException(HttpStatus.NOT_FOUND, String.format(template, "account", accountId));
        return account;
    }

    protected Account findAccount(String accountSn) {
        Account account = accountService.findBySn(accountSn);
        if( account == null )
            throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the account sn = " + accountSn);
        return account;
    }

    protected ItsnowHost findHost(long hostId) {
        ItsnowHost host = hostService.findById(hostId);
        if( host == null )
            throw new WebClientSideException(HttpStatus.NOT_FOUND, String.format(template, "host", hostId));
        return host;
    }

    protected void findSchema(long schemaId) {
        ItsnowSchema schema = schemaService.findById(schemaId);
        if( schema == null ) throw new WebClientSideException(HttpStatus.NOT_FOUND, String.format(template, "schema", schemaId));
    }

}