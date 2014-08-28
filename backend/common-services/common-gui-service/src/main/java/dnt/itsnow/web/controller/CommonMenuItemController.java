package dnt.itsnow.web.controller;

import dnt.itsnow.model.MenuItem;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.CommonMenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1>菜单服务的控制器</h1>
 * <pre>
 * <b>HTTP    URI                                 方法      含义  </b>
 * # GET      /api/menu-item                      show      列出所有菜单
 * # POST     /api/menu-item                      create    创建一个菜单项
 * # PUT      /api/menu-item/{id}                 update    修改菜单项
 *
 */
@RestController
@RequestMapping("/api/menu-item")
public class CommonMenuItemController extends ApplicationController {

    @Autowired
    private CommonMenuItemService commonMenuItemManager;

    /**
     * <h2>展示用户菜单</h2>
     * <p></p>
     * GET /api/menu-item
     * @param
     * @return 菜单项列表
     */
    @RequestMapping
    @ResponseBody
    public List<MenuItem> show(){
        logger.debug("Listing menuItem");
        return commonMenuItemManager.findAll();
    }

    /**
     * <h2>创建用户菜单</h2>
     * <p></p>
     * POST /api/menu-item
     * @param menuItem MenuItem实例
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody @Valid  MenuItem menuItem){
        try{
            commonMenuItemManager.create(menuItem);
        }catch(Exception e){
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * <h2>修改用户菜单</h2>
     * <p></p>
     * PUT /api/menu-item
     * @param menuItem MenuItem实例
     * @return
     */
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public void update(@PathVariable("id") Long id, @RequestBody @Valid  MenuItem menuItem){
        try{
            MenuItem mi = commonMenuItemManager.find(id);
            mi.apply(menuItem);
            commonMenuItemManager.update(mi);
        }catch(Exception e){
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
