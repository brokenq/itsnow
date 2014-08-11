/**
 * xiongjie on 14-8-1.
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.model.Site;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Sites Controller</h1>
 */
@RestController
@RequestMapping("/api/sites")
public class SitesController extends SessionSupportController<Site> {
    Site site;

    /**
     * <h2>获得所有的地点</h2>
     *
     * GET /api/sites
     *
     * @return 地点
     */
    @RequestMapping
    public List<Site> index(){
        return null;
    }

    /**
     * <h2>查看一个地点</h2>
     *
     * GET /api/sites/{no}
     *
     * @return 地点
     */
    @RequestMapping("{no}")
    public Site show(){
        return site;
    }

    /**
     * <h2>创建一个地点</h2>
     *
     * POST /api/sites
     *
     * @return 新建的地点
     */
    @RequestMapping(method = RequestMethod.POST)
    public Site create(@Valid @RequestBody Site site){
        return site;
    }

    /**
     * <h2>更新一个地点</h2>
     *
     * PUT /api/sites/{sn}
     *
     * @return 被更新的地点
     */
    @RequestMapping(value = "{no}", method = RequestMethod.PUT)
    public Site update(@Valid @RequestBody Site site){
        this.site.apply(site);
        //TODO SAVE IT
        return this.site;
    }

    /**
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/sites/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "{no}", method = RequestMethod.DELETE)
    public Site destroy(){
        return null;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initSite(@PathVariable("no") String no){
        site = null;//find it by sn
    }
}
