/**
 * Developer: Kadvin Date: 14-9-15 下午3:30
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.ItsnowHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    public List<ItsnowHost> index( @RequestParam(value = "keyword", required = false) String keyword ) {
        logger.debug("Listing itsnow hosts");
        indexPage = hostService.findAll(keyword, pageRequest);
        logger.debug("Found   itsnow hosts {}", indexPage.getTotalElements());
        return indexPage.getContent();
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
        try {
            hostService.delete(currentHost);
        } catch (ItsnowHostException e) {
            throw new WebClientSideException(NOT_ACCEPTABLE, e.getMessage());
        }
    }


    @BeforeFilter({"show", "start", "stop", "cancel", "destroy"})
    public void initCurrentHost(@PathVariable("id") Long id){
        logger.debug("Finding itsnow host id = {}", id);
        currentHost = hostService.findById(id);
        if( currentHost == null )
            throw new WebClientSideException(NOT_FOUND, "Can't find the itsnow host with id = " + id);
    }
}