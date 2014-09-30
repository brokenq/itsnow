package dnt.itsnow.web.controller;

import dnt.itsnow.model.Incident;
import dnt.itsnow.model.MspIncident;
import dnt.itsnow.platform.web.annotation.AfterFilter;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.service.MspIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * <h1>MSU Incident服务的控制器</h1>
 * <pre>
 * <b>HTTP     URI                                    方法            含义  </b>
 * # GET      /api/msp-incidents/                     index           列出所有当前用户的故障单列表，支持过滤，分页，排序等
 * # GET      /api/msp-incidents/closed               indexClosed     列出当前用户已关闭故障单列表
 * # GET      /api/msp-incidents/created              indexCreated    列出当前用户创建故障单列表
 * # POST     /api/msp-incidents/start                start           启动故障流程实例
 * # GET      /api/msp-incidents/{instanceId}         query           查询实例ID为{instanceId}的流程的状态，返回task信息
 * # PUT      /api/msp-incidents/{instanceId}/{taskId}/complete    complete        完成流程task
 * </pre>
 */
@RestController
@RequestMapping("/api/msp-incidents")
public class MspIncidentController extends SessionSupportController<Incident> {

    @Autowired
    MspIncidentService service;

    /**
     * <h2>查询所有当前用户的故障单列表</h2>
     * <p/>
     * GET /api/msp-incidents
     * @param key 查询关键字
     * @return  Incident列表
     */
    @RequestMapping()
    @ResponseBody
    public List<Incident> index(@RequestParam(value = "key", required = false) String key) {
        logger.debug("finding all incidents by user:{} key:{}",currentUser.getUsername(),key);
        //根据实例查询对应表单数据
        indexPage = service.findByUserAndKey(currentUser.getUsername(), key, pageRequest);
        logger.debug("found incidents:{}",indexPage.getTotalElements());
        return indexPage.getContent();
    }

    /**
     * <h2>查询当前用户的已关闭故障单列表</h2>
     * <p/>
     * GET /api/msp-incidents/closed
     * @param key 查询关键字
     * @return  Incident列表
     */
    @RequestMapping(value = "/closed")
    @ResponseBody
    public List<Incident> indexClosed(@RequestParam(value = "key", required = false) String key) {
        logger.debug("finding all closed incidents by user:{} key:{}",currentUser.getUsername(),key);
        //根据实例查询对应表单数据
        indexPage = service.findClosedByUserAndKey(currentUser.getUsername(), key, pageRequest);
        logger.debug("found closed incidents:{}",indexPage.getTotalElements());
        return indexPage.getContent();
    }

    /**
     * <h2>查询当前用户的创建的故障单列表</h2>
     * <p/>
     * GET /api/msu-incidents/created
     * @param key 查询关键字
     * @return  Incident列表
     */
    @RequestMapping("created")
    @ResponseBody
    public List<Incident> indexCreated(@RequestParam(value = "key", required = false) String key) {
        logger.debug("finding all created incidents by user:{} key:{}",currentUser.getUsername(),key);
        //根据实例查询对应表单数据
        indexPage = service.findAllCreatedByUserAndKey(currentUser.getUsername(), key, pageRequest);
        logger.debug("found created incidents:{}",indexPage.getTotalElements());
        return indexPage.getContent();
    }


    /**
     * <h2>查询流程实例ID对应的故障单信息</h2>
     * <p/>
     * GET /api/msp-incidents/{instanceId}
     * @param instanceId 流程实例ID
     * @param withHistory 是否返回历史记录
     * @return 故障单信息以及当前的task列表
     */
    @RequestMapping(value = "/{instanceId}")
    @ResponseBody
    public MspIncident query(@PathVariable("instanceId") String instanceId,
                             @RequestParam(value = "withHistory", required = false) boolean withHistory) {

        return service.findByInstanceId(instanceId,withHistory);
    }

    /**
     * <h2>启动MSU Incident流程实例</h2>
     * <p/>
     * POST /api/msp-incidents/start
     * @param incident 故障表单
     * @return 创建之后的流程实例信息
     */
    @RequestMapping(value = "/start",method = RequestMethod.POST)
    @ResponseBody
    public MspIncident start(@RequestBody @Valid Incident incident){
        return service.startIncident(mainAccount.getName(), this.currentUser.getUsername(),incident);
    }

    /**
     * <h2>完成流程中的task，提交表单数据</h2>
     * <p/>
     * PUT /api/msp-incidents/{instanceId}/{taskId}/complete
     * @param taskId    任务ID
     * @param instanceId 流程实例ID
     * @param incident 故障表单
     * @return MspIncident
     */
    @RequestMapping(value = "/{instanceId}/{taskId}/complete",method = RequestMethod.PUT)
    @ResponseBody
    public MspIncident complete(@PathVariable("instanceId") String instanceId,@PathVariable("taskId") String taskId,@RequestBody @Valid Incident incident){
        return service.processIncident(instanceId,taskId,currentUser.getUsername(),incident);
    }


    @BeforeFilter(method = RequestMethod.GET, value = {"index","indexClosed","indexCreated"})
    public void initDefaultPageRequest( @RequestParam(required = false, value = "page", defaultValue = "1") int page,
                                        @RequestParam(required = false, value = "size", defaultValue = "40") int size,
                                        @RequestParam(required = false, value = "sort", defaultValue = "") String sort){
        super.initDefaultPageRequest(page,size,sort);
    }

    @AfterFilter(method =  RequestMethod.GET, value = {"index","indexClosed","indexCreated"})
    public void renderPageToHeader(HttpServletResponse response){
        super.renderPageToHeader(response);
    }

}
