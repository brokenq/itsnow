/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.DictionaryException;
import dnt.itsnow.model.Dictionary;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>字典控制器</h1>
 * <pre>
 * <b>HTTP   URI                         方法      含义  </b>
 *  GET      /api/dictionaries           index   列出所有的字典，并且分页显示
 *  GET      /api/dictionaries/{code}    show    列出特定的字典记录
 *  POST     /api/dictionaries           create  创建一个字典
 *  PUT      /api/dictionaries/{code}    update  修改一个指定的字典
 *  DELETE   /api/dictionaries/{code}    delete  删除指定的字典记录
 *  GET      /api/dictionaries/checkCode/{code}    get  检查字典代码是否唯一
 * </pre>
 */
@RestController
@RequestMapping("/api/dictionaries")
public class DictionariesController extends SessionSupportController<Dictionary> {

    @Autowired
    private DictionaryService service;

    private Dictionary dictionary;

    /**
     * <h2>获得所有的字典</h2>
     * <p/>
     * GET /api/dictionaries
     *
     * @param keyword 关键字
     * @return 字典列表
     */
    @RequestMapping
    public Page<Dictionary> index(@RequestParam(value = "keyword", required = false) String keyword) {

        logger.debug("Listing dictionaries by keyword: {}", keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed  {}", indexPage);

        return indexPage;
    }
//------------------------------------

    /**
     * 检验字典代码是否唯一
     * @param code
     * @return
     */
    @RequestMapping("checkCode/{code}")
    public HashMap checkCode(@PathVariable("code") String code){

        dictionary = service.findByCode(code);
        if(dictionary != null){
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate dict code: " + code);
        }

        return new HashMap();
    }


    /**
     * 检验字典名称是否唯一
     * @param name
     * @return
     */
    @RequestMapping("checkname/{name}")
    public HashMap checkName(@PathVariable("name") String name){

        dictionary = service.findByName(name);
        if(dictionary != null){
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate dict name: " + name);
        }

        return new HashMap();
    }

    /**
     * 检查展示名是否唯一
     * @param label
     * @return
     */
    @RequestMapping("checklabel/{label}")
    public HashMap checkLabel(@PathVariable("label") String label){

        dictionary = service.findByLabel(label);
        if(dictionary != null){
            throw new WebClientSideException(HttpStatus.CONFLICT, "Duplicate dict label: " + label);
        }

        return new HashMap();
    }




    /**
     * <h2>查看一个字典</h2>
     * <p/>
     * GET /api/dictionaries/{code}
     *
     * @return 字典
     */
    @RequestMapping("{code}")
    public Dictionary show() {
        return dictionary;
    }

//-----------------------------------------------------------------------------
    /**
     * <h2>创建一个字典</h2>
     * <p/>
     * POST /api/dictionaries
     *
     * @param dictionary 待创建字典
     * @return 新建的字典
     */
    @RequestMapping(method = RequestMethod.POST)
    public Dictionary create(@Valid @RequestBody Dictionary dictionary) {

        logger.info("Creating {}", dictionary);

        try {
            dictionary = service.create(dictionary);
        } catch (DictionaryException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created  {}", dictionary);

        return dictionary;
    }
//-----------------------------------------------------------------------------------
    /**
     * <h2>更新一个字典</h2>
     * <p/>
     * PUT /api/dictionaries/{code}
     *
     * @param dictionary 字典
     * @return 被更新的字典
     */
    @RequestMapping(value = "{code}", method = RequestMethod.PUT)
    public Dictionary update(@Valid @RequestBody Dictionary dictionary) {

        logger.info("Updating {}", dictionary);

        this.dictionary.apply(dictionary);
        try {
            service.update(this.dictionary);
        } catch (DictionaryException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated  {}", this.dictionary);

        return this.dictionary;
    }
//-------------------------------------------------------------------------------------
    /**
     * <h2>删除一个字典</h2>
     * <p/>
     * DELETE /api/dictionaries/{code}
     *
     * @return 被删除的字典
     */
    @RequestMapping(value = "{code}", method = RequestMethod.DELETE)
    public Dictionary destroy() {

        logger.warn("Deleting {}", dictionary);

        try {
            service.destroy(dictionary);
        } catch (DictionaryException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.warn("Deleted  {}", dictionary);

        return dictionary;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initProcessDictionary(@PathVariable("code") String code) {
        this.dictionary = service.findByCode(code);//find it by code
    }
}
