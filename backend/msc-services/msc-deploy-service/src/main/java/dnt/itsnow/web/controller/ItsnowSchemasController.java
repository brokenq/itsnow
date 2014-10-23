package dnt.itsnow.web.controller;

import dnt.itsnow.exception.ItsnowSchemaException;
import dnt.itsnow.model.ItsnowSchema;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.ItsnowSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * <h1>Itsnow schemas web controller</h1>
 * <pre>
 * <b>HTTP     URI               方法       含义  </b>
 * GET    /admin/api/schemas       index     列出所有Schema，支持过滤，分页，排序等
 * GET    /admin/api/schemas/{id}  show      查看特定的Schema信息
 * POST   /admin/api/schemas       create    创建Schema资源
 * PUT    /admin/api/schemas/{id}  update    修改Schema信息
 * DELETE /admin/api/schemas/{id}  destroy   删除特定的Schema信息
 * </pre>
 */
@RestController
@RequestMapping("/admin/api/schemas")
public class ItsnowSchemasController extends SessionSupportController<ItsnowSchema>{
    @Autowired
    ItsnowSchemaService service;
    ItsnowSchema currentSchema;

    /**
     * <h2>查看所有的Schema资源</h2>
     * <p/>
     * GET /admin/api/schemas?page={int}&count={int}&sort={string}&keyword={string}
     *
     * @param keyword Schema名称/描述中的关键词
     * @return 主机列表
     */
    @RequestMapping
    public Page<ItsnowSchema> index( @RequestParam(value = "keyword", required = false) String keyword ) {
        logger.debug("Listing itsnow schemas by keyword: {}", keyword);
        indexPage = service.findAll(keyword, pageRequest);
        logger.debug("Found   {}", indexPage);
        return indexPage;
    }

    /**
     * <h2>查看特定Schema资源</h2>
     *
     * GET /admin/api/hosts/{id}
     */
    @RequestMapping("{id}")
    public ItsnowSchema show() {
        logger.debug("Viewed  {}", currentSchema);
        return currentSchema;
    }

    /**
     * <h2>创建Schema</h2>
     * <p/>
     * POST /admin/api/schemas
     *
     * @param creating 通过http post body以JSON等方式提交的数据
     * @return 刚刚创建的Schema对象信息
     */
    @RequestMapping(method = RequestMethod.POST)
    public ItsnowSchema create(@Valid @RequestBody ItsnowSchema creating){
        logger.info("Creating {} Itsnow Schema {}", creating);
        try{
            currentSchema = service.create(creating);
            logger.info("Created  {} Itsnow Schema {} ", currentSchema);
            return currentSchema;
        }catch (ItsnowSchemaException ex){
            throw new WebClientSideException(BAD_REQUEST, ex.getMessage());
        }
    }

    /**
     * <h2>删除特定Schema资源</h2>
     * <p/>
     * DELETE /admin/api/schemas/{id}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void destroy() {
        logger.debug("Destroying {}", currentSchema);
        if( !service.canDelete(currentSchema) )
            throw new WebClientSideException(NOT_ACCEPTABLE, "Can't delete the itsnow schema for which is associated with the active processes");
        try {
            service.delete(currentSchema);
        } catch (ItsnowSchemaException e) {
            throw new WebClientSideException(NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @BeforeFilter({"show", "destroy"})
    public void initCurrentSchema(@PathVariable("id") Long id){
        logger.debug("Finding itsnow schema id = {}", id);
        currentSchema = service.findById(id);
        if( currentSchema == null )
            throw new WebClientSideException(NOT_FOUND, "Can't find the itsnow schema with id = " + id);
    }
}
