package dnt.itsnow.web.controller;

import dnt.itsnow.Exception.SiteException;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>Sites Controller</h1>
 */
@RestController
@RequestMapping("/api/sites")
public class SitesController extends SessionSupportController<Site> {

    @Autowired
    private SiteService siteService;

    private Site site;

    /**
     * <h2>获得所有的地点</h2>
     *
     * GET /api/sites
     *
     * @return 地点列表
     */
    @RequestMapping
    public List<Site> index(@RequestParam(value = "keyword", required = false) String keyword){
        logger.debug("Listing Site");

        indexPage = siteService.findAll(keyword, pageRequest);

        logger.debug("Listed Site number {}", indexPage.getNumber());
        return indexPage.getContent();
    }

    /**
     * <h2>查看一个地点</h2>
     *
     * GET /api/sites/{sn}
     *
     * @return 地点
     */
    @RequestMapping("/{sn}")
    public Site show(){
        if (site == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The site no must be specified");
        }
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
        logger.info("Creating {}", site.getName());

        try {
            site = siteService.create(site);
        } catch (SiteException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Created  {}", site.getName());
        return site;
    }

    /**
     * <h2>更新一个地点</h2>
     *
     * PUT /api/sites/{sn}
     *
     * @return 被更新的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public Site update(@Valid @RequestBody Site site){

        logger.info("Updateing {}", site.getName());

        if (site == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The site no must be specified");
        }

        this.site.apply(site);
        try {
            siteService.update(site);
        } catch (SiteException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        logger.info("Updated {}", site.getName());

        return this.site;
    }

    /**
     * <h2>删除一个地点</h2>
     *
     * DELETE /api/sites/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public Site destroy(){

        if (site == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The site no must be specified");
        }

        try {
            siteService.destroy(site);
        } catch (SiteException e) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return site;
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initSite(@PathVariable("sn") String sn){

        this.site = siteService.findBySn(sn);//find it by sn
    }
}
