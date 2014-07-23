/**
 * Developer: Kadvin Date: 14-7-14 下午5:08
 */
package dnt.itsnow.web.controller;

import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.PageRequest;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.api.ServiceCatalogService;
import dnt.itsnow.model.ServiceCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The user controller
 */
@RestController
@RequestMapping("/api/servicecatalog")
public class ServiceCatalogController extends ApplicationController {

    @Autowired
    ServiceCatalogService scService;

    /**
     * <h2>查询服务目录列表</h2>
     *
     * GET /api/servicecatalog?keyword=theKeyWord&page=1
     *
     * @param keyword 服务目录特征词，可能没有
     * @param page 第几页
     * @param size 分页参数
     *             即便这个值被放到用户profile,或者session里面
     *             那也是前端程序读取到这个值，而后传递过来，而不是这里去读取
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<ServiceCatalog> all(@RequestParam(required = false, defaultValue = "") String keyword,
                          @RequestParam(required = false, defaultValue = "0") int page,
                          @RequestParam(required = false, defaultValue = "40") int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<ServiceCatalog> thePage = scService.findAll(keyword, pageable);
        return thePage.getContent();
    }

    /**
     * <h2>查看特定服务目录信息</h2>
     *
     * GET /api/servicecatalog/1
     *
     * @param id 服务目录id
     * @return 服务目录信息
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ServiceCatalog find(@PathVariable("id") Integer id ){
        return scService.find(id);
    }

    /**
     * POST /api/servicecatalog/
     * @param sc 服务目录对象
     * @return 服务目录对象
     */
    @RequestMapping( method = RequestMethod.POST)
    @ResponseBody
    public Object save(@RequestBody ServiceCatalog sc,HttpServletRequest request)throws Exception {
        scService.save(sc);
        return sc;
    }

    /**
     * PUT /api/servicecatalog/
     * @param sc 服务目录
     * @param request
     * @return 服务目录
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Object update(@RequestBody ServiceCatalog sc,HttpServletRequest request)throws Exception{
        scService.update(sc);
        return sc;
    }

    /**
     * DELETE /api/servicecatalog/1
     * @param id  服务目录ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id)throws Exception{
        scService.delete(id);
        String result="{'result':'true'}";
        return result;
    }
}
