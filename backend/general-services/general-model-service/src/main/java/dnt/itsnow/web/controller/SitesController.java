package dnt.itsnow.web.controller;

import dnt.itsnow.exception.SiteException;
import dnt.itsnow.model.Site;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.platform.web.exception.WebServerSideException;
import dnt.itsnow.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <h1>地点的控制器</h1>
 * <pre>
 * <b>HTTP     URI                         方法      含义  </b>
 * # GET      /admin/api/sites?keyword={}  index     列出所有地点，支持过滤，分页，排序等
 * # GET      /admin/api/sites             show      列出特定的地点
 * # POST     /admin/api/sites             create    创建地点，账户信息通过HTTP BODY提交
 * # PUT      /admin/api/sites/{sn}        update    修改地点，账户信息通过HTTP BODY提交
 * # DELETE   /admin/api/sites/{sn}        destroy   删除地点
 * </pre>
 */
@RestController
@RequestMapping("/api/sites")
public class SitesController extends SessionSupportController<Site> {

    @Autowired
    private SiteService siteService;

    private Site site;

    /**
     * <h2>获得所有的地点</h2>
     * <p/>
     * GET /api/sites
     *
     * @param keyword 查询关键字
     * @return 地点列表
     */
    @RequestMapping
    public Page<Site> index(@RequestParam(value = "keyword", required = false) String keyword) {
        logger.debug("Listing Sites by keyword: {}" + keyword);

        indexPage = siteService.findAll(keyword, pageRequest);

        logger.debug("Listed  {}", indexPage);
        return indexPage;
    }

    /**
     * <h2>查看一个地点</h2>
     * <p/>
     * GET /api/sites/{sn}
     *
     * @return 地点
     */
    @RequestMapping("/{sn}")
    public Site show() {
        return site;
    }

    /**
     * <h2>创建一个地点</h2>
     * <p/>
     * POST /api/sites
     * @param site 待新建的地点
     * @return 新建的地点
     */
    @RequestMapping(method = RequestMethod.POST)
    public Site create(@Valid @RequestBody Site site) {
        logger.info("Creating {}", site);

        try {
            site = siteService.create(site);
        } catch (SiteException e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Created {}", site);
        return site;
    }

    /**
     * <h2>更新一个地点</h2>
     * <p/>
     * PUT /api/sites/{sn}
     * @param site 待更新的地点
     * @return 被更新的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.PUT)
    public Site update(@Valid @RequestBody Site site) {

        logger.info("Updating {}", site);

        this.site.apply(site);
        try {
            siteService.update(site);
        } catch (SiteException e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }

        logger.info("Updated {}", site);

        return this.site;
    }

    /**
     * <h2>删除一个地点</h2>
     * <p/>
     * DELETE /api/sites/{sn}
     *
     * @return 被删除的地点
     */
    @RequestMapping(value = "/{sn}", method = RequestMethod.DELETE)
    public void destroy() {
        try {
            siteService.destroy(site);
        } catch (SiteException e) {
            throw new WebServerSideException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    @BeforeFilter({"show", "update", "destroy"})
    public void initSite(@PathVariable("sn") String sn) {

        this.site = siteService.findBySn(sn);//find it by sn

        if (site == null) {
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, "Invalid serial number.");
        }

    }
}
