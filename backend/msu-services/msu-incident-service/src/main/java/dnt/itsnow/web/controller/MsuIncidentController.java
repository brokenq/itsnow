package dnt.itsnow.web.controller;

import dnt.itsnow.model.Incident;
import dnt.itsnow.model.MsuIncident;
import dnt.itsnow.platform.web.annotation.AfterFilter;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.service.MsuIncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * <h1>MSU Incident服务的控制器</h1>
 * <pre>
 * <b>HTTP     URI                                    方法            含义  </b>
 * # GET      /api/msu-incidents/                     index           列出所有当前用户的故障单列表，支持过滤，分页，排序等
 * # GET      /api/msu-incidents/closed               indexClosed     列出当前用户已关闭故障单列表
 * # GET      /api/msu-incidents/created              indexCreated    列出当前用户创建的故障单列表
 * # POST     /api/msu-incidents/start                start           启动故障流程实例
 * # GET      /api/msu-incidents/{instanceId}         query           查询实例ID为{instanceId}的流程的状态，返回task信息
 * # PUT      /api/msu-incidents/{instanceId}/{taskId}/complete    complete        完成流程task
 * </pre>
 */
@RestController
@RequestMapping("/api/msu-incidents")
public class MsuIncidentController extends SessionSupportController<Incident> {

    @Autowired
    MsuIncidentService service;

    /**
     * <h2>查询所有当前用户的故障单列表</h2>
     * <p/>
     * GET /api/msu-incidents
     * @param key 查询关键字
     * @return  Incident列表
     */
    @RequestMapping
    public List<Incident> index(@RequestParam(value = "key", required = false) String key) {

        //根据实例查询对应表单数据
        indexPage = service.findAllByUserAndKey(currentUser.getUsername(), key, pageRequest);
        return indexPage.getContent();
    }

    /**
     * <h2>查询当前用户的已关闭故障单列表</h2>
     * <p/>
     * GET /api/msu-incidents/closed
     * @param key 查询关键字
     * @return  Incident列表
     */
    @RequestMapping("closed")
    @ResponseBody
    public List<Incident> indexClosed(@RequestParam(value = "key", required = false) String key) {

        //根据实例查询对应表单数据
        indexPage = service.findAllClosedByUserAndKey(currentUser.getUsername(), key, pageRequest);
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

        //根据实例查询对应表单数据
        indexPage = service.findAllCreatedByUserAndKey(currentUser.getUsername(), key, pageRequest);
        return indexPage.getContent();
    }


    /**
     * <h2>查询流程实例ID对应的故障单信息</h2>
     * <p/>
     * GET /api/msu-incidents/{instanceId}
     * @param instanceId 流程实例ID
     * @param withHistory 是否返回历史信息
     * @return 故障单信息以及当前的task列表
     */
    @RequestMapping("{instanceId}")
    @ResponseBody
    public MsuIncident query(@PathVariable("instanceId") String instanceId,
                                    @RequestParam(value = "withHistory", required = false) boolean withHistory) {

       return service.findByInstanceId(instanceId,withHistory);
    }

    /**
     * <h2>启动MSU Incident流程实例</h2>
     * <p/>
     * POST /api/msu-incidents/start
     * @param incident 故障表单
     * @return 故障信息
     */
    @RequestMapping(value = "start",method = RequestMethod.POST)
    @ResponseBody
    public MsuIncident start(@RequestBody @Valid Incident incident){
        return service.startIncident(mainAccount.getName(), this.currentUser.getUsername(),incident);
    }

    /**
     * <h2>完成流程中的task，提交表单数据</h2>
     * <p/>
     * PUT /api/msu-incidents/{instanceId}/{taskId}/complete
     * @param instanceId 流程实例ID
     * @param taskId    任务ID
     * @param incident 故障表单
     * @return MsuIncident
     */
    @RequestMapping(value = "{instanceId}/{taskId}/complete",method = RequestMethod.PUT)
    @ResponseBody
    public MsuIncident complete(@PathVariable("instanceId") String instanceId,@PathVariable("taskId") String taskId,@RequestBody @Valid Incident incident){
        return service.processIncident(instanceId,taskId,currentUser.getUsername(),incident);
    }

    @Override
    @BeforeFilter(method = RequestMethod.GET, value = {"index","indexClosed","indexCreated"})
    public void initDefaultPageRequest( @RequestParam(required = false, value = "page", defaultValue = "1") int page,
                                        @RequestParam(required = false, value = "size", defaultValue = "40") int size,
                                        @RequestParam(required = false, value = "sort", defaultValue = "") String sort){
        super.initDefaultPageRequest(page,size,sort);
    }

    @Override
    @AfterFilter(method =  RequestMethod.GET, value = {"index","indexClosed","indexCreated"})
    public void renderPageToHeader(HttpServletResponse response){
        super.renderPageToHeader(response);
    }

}
