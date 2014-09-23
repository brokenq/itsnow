/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.MspGroup;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MspGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>流程字典的控制器</h1>
 * <pre>
 * <b>HTTP    URI                 方法      含义  </b>
 *  GET      /api/msp-groups           index     列出所有的流程字典，并且分页显示
 *  GET      /api/msp-groups/{sn}      show      列出特定的流程字典记录
 *  POST     /api/msp-groups/          create    创建一个流程字典
 *  PUT      /api/msp-groups/{sn}      update    修改一个指定的流程字典
 *  DELETE   /api/msp-groups/{sn}      delete    删除指定的流程字典记录
 * </pre>
 */
@RestController
@RequestMapping("/api/msp-groups")
public class MspGroupsController extends SessionSupportController<MspGroup> {

    @Autowired
    private MspGroupService service;

    private MspGroup group;

    // 虽然也是index，但这里不需要分页，application controller 也不会错
    @RequestMapping
    public List<MspGroup> index(@RequestParam(value = "keyword", required = false) String keyword) {
        logger.debug("Listing group keyword:{}" + keyword);

        indexPage = service.findAll(keyword, pageRequest);

        logger.debug("Listed group number {}", indexPage.getNumber());
        return indexPage.getContent();
    }

    @RequestMapping("/detail/{name}")
    public List<MspGroup> show(@RequestParam(value = "keyword", required = false) String keyword) {
        logger.debug("Listing group keyword:{}" + keyword);

        indexPage = service.findAllRelevantInfo(keyword, pageRequest);

        logger.debug("Listed group number {}", indexPage.getNumber());
        return indexPage.getContent();
    }

    @RequestMapping("/{name}")
    public MspGroup group(@PathVariable("name") String name){
        return service.find(name);
    }

    /**
     * <h2>创建一个地点</h2>
     *
     * POST /api/msp-groups
     *
     * @return 新建的地点
     */
    @RequestMapping(method = RequestMethod.POST)
    public MspGroup create(@Valid @RequestBody MspGroup dictionary){
        logger.info("Creating {}", dictionary.getName());

        try {
            dictionary = service.create(dictionary);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", dictionary.getName());
        return dictionary;
    }

    /**
     * <h2>更新一个地点</h2>
     *
     * PUT /api/msp-groups/{sn}
     *
     * @return 被更新的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public MspGroup update(@Valid @RequestBody MspGroup dictionary){

        logger.info("Updateing {}", dictionary.getName());

        if (dictionary == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The group no must be specified");
        }

        this.group.apply(dictionary);
        try {
            dictionary = service.update(dictionary);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", dictionary.getName());

        return dictionary;
    }

    /**
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/msp-groups/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public MspGroup destroy(){

        if (group == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The group no must be specified");
        }

        try {
            service.destroy(group);
        } catch (GroupException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return group;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initGroup(@PathVariable("sn") String sn){

        this.group = service.findBySn(sn);//find it by sn
    }
}
