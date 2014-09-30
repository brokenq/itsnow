/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.ProcessDictionaryException;
import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.ProcessDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>流程字典的控制器</h1>
 * <pre>
 * <b>HTTP    URI                 方法      含义  </b>
 *  GET      /api/process-dictionaries           index     列出所有的流程字典，并且分页显示
 *  GET      /api/process-dictionaries/{sn}      show      列出特定的流程字典记录
 *  POST     /api/process-dictionaries           create    创建一个流程字典
 *  PUT      /api/process-dictionaries/{sn}      update    修改一个指定的流程字典
 *  DELETE   /api/process-dictionaries/{sn}      delete    删除指定的流程字典记录
 * </pre>
 */
@RestController
@RequestMapping("/api/process-dictionaries")
public class ProcessDictionariesController extends SessionSupportController<ProcessDictionary> {

    @Autowired
    private ProcessDictionaryService service;

    private ProcessDictionary processDictionary;

    /**
     * <h2>获得所有的流程字典</h2>
     * <p/>
     * GET /api/process-dictionaries
     *
     * @param keyword 关键字
     * @return 字典列表
     */
    @RequestMapping
    public List<ProcessDictionary> index(@RequestParam(value = "keyword", required = false) String keyword) {

        logger.debug("Listing Process Dictionary by keyword:{}", keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed ProcessDictionary number : {}", indexPage.getContent().size());

        return indexPage.getContent();
    }

    /**
     * <h2>查看一个字典</h2>
     * <p/>
     * GET /api/process-dictionaries/{sn}
     *
     * @return 流程字典
     */
    @RequestMapping("{sn}")
    public ProcessDictionary show() {
        return processDictionary;
    }

    /**
     * 根据CODE展示一组流程字典
     *
     * @param code 字典代码
     * @return 字典列表
     */
    @RequestMapping("code/{code}")
    public ProcessDictionary list(@PathVariable(value = "code") String code) {

        logger.debug("Listing Process Dictionary by code:{}", code);

        List<ProcessDictionary> dictionaries = service.findByCode(code);

        logger.debug("Listed Process Dictionary number:{}", dictionaries.size());

        return processDictionary;
    }

    /**
     * <h2>创建一个流程字典</h2>
     * <p/>
     * POST /api/process-dictionaries
     *
     * @param dictionary 待创建字典
     * @return 新建的字典
     */
    @RequestMapping(method = RequestMethod.POST)
    public ProcessDictionary create(@Valid @RequestBody ProcessDictionary dictionary) {

        logger.info("Creating {}", dictionary);

        try {
            dictionary = service.create(dictionary);
        } catch (ProcessDictionaryException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created {}", dictionary);

        return dictionary;
    }

    /**
     * <h2>更新一个字典</h2>
     * <p/>
     * PUT /api/process-dictionaries/{sn}
     *
     * @param dictionary 字典
     * @return 被更新的字典
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.PUT)
    public ProcessDictionary update(@Valid @RequestBody ProcessDictionary dictionary) {

        logger.info("Updating {}", dictionary);

        this.processDictionary.apply(dictionary);
        try {
            dictionary = service.update(this.processDictionary);
        } catch (ProcessDictionaryException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated {}", dictionary);

        return dictionary;
    }

    /**
     * <h2>删除一个字典</h2>
     * <p/>
     * DELETE /api/process-dictionaries/{sn}
     *
     * @return 被删除的字典
     */
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public ProcessDictionary destroy() {

        logger.warn("Deleting {}", processDictionary);

        try {
            service.destroy(processDictionary);
        } catch (ProcessDictionaryException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted {}", processDictionary);

        return processDictionary;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initProcessDictionary(@PathVariable("sn") String sn) {
        this.processDictionary = service.findBySn(sn);//find it by sn
    }
}
