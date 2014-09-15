/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.ProcessDictionaryException;
import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
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
 *  POST     /api/process-dictionaries/          create    创建一个流程字典
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
     *
     * GET /api/process-dictionaries
     *
     * @return 字典列表
     */
    @RequestMapping
    public List<ProcessDictionary> index(@RequestParam(value = "keyword", required = false) String keyword){
        logger.debug("Listing ProcessDictionary");

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed ProcessDictionary number {}", indexPage.getNumber());
        return indexPage.getContent();
    }

    /**
     * <h2>查看一个字典</h2>
     *
     * GET /api/process-dictionaries/{sn}
     *
     * @return 地点
     */
    @RequestMapping("/{sn}")
    public ProcessDictionary show(){
        if (processDictionary == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The processDictionary no must be specified");
        }
        return processDictionary;
    }

    /**
     * <h2>创建一个地点</h2>
     *
     * POST /api/process-dictionaries
     *
     * @return 新建的地点
     */
    @RequestMapping(method = RequestMethod.POST)
    public ProcessDictionary create(@Valid @RequestBody ProcessDictionary dictionary){
        logger.info("Creating {}", dictionary.getName());

        try {
            dictionary = service.create(dictionary);
        } catch (ProcessDictionaryException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", dictionary.getName());
        return dictionary;
    }

    /**
     * <h2>更新一个地点</h2>
     *
     * PUT /api/process-dictionaries/{sn}
     *
     * @return 被更新的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public ProcessDictionary update(@Valid @RequestBody ProcessDictionary dictionary){

        logger.info("Updateing {}", dictionary.getName());

        if (dictionary == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The processDictionary no must be specified");
        }

        this.processDictionary.apply(dictionary);
        try {
            dictionary = service.update(dictionary);
        } catch (ProcessDictionaryException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", dictionary.getName());

        return dictionary;
    }

    /**
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/process-dictionaries/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public ProcessDictionary destroy(){

        if (processDictionary == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The processDictionary no must be specified");
        }

        try {
            service.destroy(processDictionary.getCode());
        } catch (ProcessDictionaryException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return processDictionary;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initProcessDictionary(@PathVariable("sn") String sn){

        this.processDictionary = service.findBySn(sn);//find it by sn
    }
}
