/**
 * Developer: Kadvin Date: 14-9-15 下午3:30
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.ItsnowHostService;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

/**
 * <h1>Itsnow hosts web controller</h1>
 * <pre>
 * <b>HTTP     URI               方法       含义  </b>
 * GET    /admin/api/hosts       index     列出所有主机，支持过滤，分页，排序等
 * GET    /admin/api/hosts/dbs   db_index  列出所有数据库主机
 * GET    /admin/api/hosts/{id}  show      查看特定的主机信息
 * POST   /admin/api/hosts       create    创建主机资源
 * PUT    /admin/api/hosts/{id}  update    修改主机信息
 * DELETE /admin/api/hosts/{id}  destroy   删除特定的主机信息
 * GET    /admin/api/hosts/{id}/follow/{invocationId} follow    获取目标主机最新的任务信息
 * GET    /admin/api/hosts/checkName?value=xx checkName 检查主机名是否唯一
 * GET    /admin/api/hosts/checkAddress?value=yy checkAddress 检查主机地址是否唯一,有效
 * GET    /admin/api/hosts/checkPassword?host={host}&username={username}&password={password} checkPassword 检查主机用户名密码是否有效
 * </pre>
 */
@RestController
@RequestMapping("/admin/api/hosts")
public class ItsnowHostsController extends SessionSupportController<ItsnowHost>{
    @Autowired
    ItsnowHostService hostService;
    ItsnowHost currentHost;


    /**
     * <h2>查看所有的主机资源</h2>
     * <p/>
     * GET /admin/api/hosts?page={int}&count={int}&sort={string}&keyword={string}
     *
     * @param keyword 主机名称/描述中的关键词
     * @return 主机列表
     */
    @RequestMapping
    public Page<ItsnowHost> index( @RequestParam(value = "keyword", required = false) String keyword ) {
        logger.debug("Listing itsnow hosts by keyword: {}", keyword);
        indexPage = hostService.findAll(keyword, pageRequest);
        logger.debug("Found   {}", indexPage);
        return indexPage;
    }

    /**
     * <h2>查看所有的主机资源</h2>
     * <p/>
     * GET /admin/api/hosts/dbs
     *
     * @return 主机列表
     */
    @RequestMapping("dbs")
    public List<ItsnowHost> db_index() {
        logger.debug("Listing itsnow db hosts");
        List<ItsnowHost> dbHosts = hostService.findAllDbHosts();
        logger.debug("Found   itsnow db hosts {}", dbHosts.size());
        return dbHosts;
    }

    /**
     * <h2>查看特定主机资源</h2>
     *
     * 备注：由于地址包括点号，
     *  GET /admin/api/hosts/192.168.0.100 会被理解为
     *  URI = /admin/api/hosts/192.168.0, format = 100
     * 所以地址用id做参数表达，而不是地址这个更有意义的数值
     * <p/>
     * GET /admin/api/hosts/{id}
     */
    @RequestMapping("{id}")
    public ItsnowHost show() {
        logger.debug("Viewed  {}", currentHost);
        return currentHost;
    }

    /**
     * <h2>创建(分配)主机资源</h2>
     * <p/>
     * POST /admin/api/hosts
     *
     * @param creating 通过http post body以JSON等方式提交的数据
     * @return 刚刚创建的主机对象信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public ItsnowHost create(@Valid @RequestBody ItsnowHost creating){
        logger.info("Creating {} Itsnow Host {}", creating);
        try{
            // 可能会抛出重名的异常(重名由数据库uk保证)
            currentHost= hostService.create(creating);
            logger.info("Created  {} Itsnow Host {} ", currentHost);
            return currentHost;
        }catch (ItsnowHostException ex){
            throw new WebClientSideException(BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * <h2>删除特定主机资源</h2>
     * <p/>
     * DELETE /admin/api/hosts/{id}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void destroy() {
        logger.debug("Destroying {}", currentHost);
        if( !hostService.canDelete(currentHost) )
            throw new WebClientSideException(NOT_ACCEPTABLE, "Can't delete the itsnow host for which is associated with the active processes or schemas");
        try {
            hostService.delete(currentHost);
        } catch (ItsnowHostException e) {
            throw new WebClientSideException(NOT_ACCEPTABLE, e.getMessage());
        }
    }

    /**
     * <h2>查看特定任务的最新信息</h2>
     * <p/>
     * PUT /admin/api/hosts/{id}/follow/{invocationId}
     * <pre>
     * Response body:
     *   返回从offset行开始的任务的输出信息，如果offset值不正确，则以错误响应
     * Response Header:
     *   offset: 下一次来获取信息应该传入的offset值
     * </pre>
     */
    @RequestMapping("{id}/follow/{invocationId}")
    public String follow( @PathVariable("invocationId") String invocationId,
                          @RequestParam( value = "offset", defaultValue = "0") long offset,
                          HttpServletResponse response){
        logger.debug("Follow {} invocation {} from {}", currentHost, invocationId, offset);
        List<String> body = new LinkedList<String>();
        offset = hostService.follow(currentHost, invocationId, offset, body);
        response.setHeader("offset", String.valueOf(offset));
        return StringUtils.join(body, "\n");
    }

    /**
     * <h2>检查主机名是否有效</h2>
     * <p/>
     * 主要检查主机的名称是否唯一；
     * 如果主机名可以直接ping通，则会返回目标主机的ip地址；
     * 如果ping不通，只要名称合法，也不认为是错误，但http body里面没有ip地址
     * PUT /admin/api/hosts/checkName?value=
     * <pre>
     * Response body:
     * 200: {'address': '172.16.1.32'}
     * </pre>
     */
    @RequestMapping("checkName")
    public HashMap checkName(@RequestParam("value") String value ){
      ItsnowHost host = hostService.findByName(value);
      if(host != null) 
          throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate host name: " + value);

      HashMap<String,String> map = new HashMap<String,String>();
      try{
        String address = hostService.resolveAddress(value);
        map.put("address", address);
      }catch(ItsnowHostException ex){
        // do not render any address indicator
      }
      return map;
    }

    /**
     * <h2>检查主机地址是否有效</h2>
     * <p/>
     * 1. 检查主机的地址是否物理唯一；
     * 2. 检查主机地址可以直接ping通
     * GET /admin/api/hosts/checkAddress?value=address
     * <pre>
     * Response body:
     * 200: {'name': 'dev2'}
     * </pre>
     */
    @RequestMapping("checkAddress")
    public HashMap checkAddress(@RequestParam("value")  String value ){
      HashMap<String,String> map = new HashMap<String,String>();
      String name;
      try{
        name = hostService.resolveName(value);
      }catch(ItsnowHostException ex){
        // do not render any name indicator
        throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't reach host address: " + value);
      }
      ItsnowHost host = hostService.findByAddress(value);
      if(host != null) 
          throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate host address: " + value);

      host = hostService.findByName(name);
      if(host != null) 
          throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate host name: " + name);
      map.put("name", name);
      return map;
    }

    /**
     * <h2>检查主机用户名密码是否有效</h2>
     * <p/>
     * GET /admin/api/hosts/checkPassword?host={host}&username={username}&password={password}
     *  @param host 主机地址
     *  @param username 用户名
     *  @param password 密码
     *  <pre>
     *    Response body:
     *    200: {}
     *  </pre>
     */
    @RequestMapping("checkPassword")
    public Map checkPassword(@RequestParam("host") String host,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password) {
        try {
            boolean correct = hostService.checkPassword(host, username, password);
            if( !correct ) throw new WebClientSideException(CONFLICT, "bad user or password");
            return new HashMap();
        } catch (ItsnowHostException e) {
            throw new WebServerSideException(INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @BeforeFilter({"show", "start", "stop", "cancel", "destroy", "follow"})
    public void initCurrentHost(@PathVariable("id") Long id){
        logger.debug("Finding itsnow host id = {}", id);
        currentHost = hostService.findById(id);
        if( currentHost == null )
            throw new WebClientSideException(NOT_FOUND, "Can't find the itsnow host with id = " + id);
    }

}
