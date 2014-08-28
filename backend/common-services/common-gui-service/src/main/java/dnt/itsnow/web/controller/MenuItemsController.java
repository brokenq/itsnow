package dnt.itsnow.web.controller;

import dnt.itsnow.exception.MenuItemException;
import dnt.itsnow.model.MenuItem;
import dnt.itsnow.platform.web.annotation.BeforeFilter;
import dnt.itsnow.platform.web.controller.ApplicationController;
import dnt.itsnow.platform.web.exception.WebClientSideException;
import dnt.itsnow.service.MenuItemService;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <h1>菜单服务的控制器</h1>
 * <pre>
 * <b>HTTP    URI                                     方法      含义  </b>
 *  GET      /api/menu_items/{path}?tree={bool}      index     列出特定菜单下所有在子菜单，如果没有设定menu path，则列出所有菜单
 *  POST     /api/menu_items/{path}                  create    在特定 menu 下建一个子菜单项
 *  PUT      /api/menu_items/{path}                  update    修改菜单项
 * </pre>
 */
@RestController
@RequestMapping("/api/menu_items/**")
public class MenuItemsController extends ApplicationController {

    @Autowired
    private MenuItemService menuItemService;

    private MenuItem target;

    /**
     * <h2>展示用户菜单</h2>
     * <p></p>
     * GET /api/menu_items/{path}?tree={bool}
     * @return 菜单项列表
     */
    @RequestMapping
    @ResponseBody
    public List<MenuItem> index(@RequestParam( value = "tree", defaultValue = "true") boolean tree){
        logger.debug("Listing menuItem");
        if(target == null )
            return menuItemService.findAll(tree);
        else
            return menuItemService.findAllByParent(target, tree);
    }

    /**
     * <h2>创建用户菜单</h2>
     * <p></p>
     * POST /api/menu_items/{path}
     * @param menuItem MenuItem实例
     * @return created menu item
     */
    @RequestMapping(method = RequestMethod.POST)
    public MenuItem create(@RequestBody @Valid MenuItem menuItem){
        try{
            return menuItemService.create(target, menuItem);
        }catch(MenuItemException e){
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * <h2>修改用户菜单</h2>
     * <p></p>
     * PUT /api/menu_items/{path}
     * @param menuItem MenuItem实例
     * @return updated menu item
     */
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public MenuItem update(@PathVariable("id") Long id, @RequestBody @Valid MenuItem menuItem){
        if( target == null ) throw new WebClientSideException(HttpStatus.BAD_REQUEST, "The menu item path must be specified");
        try{
            target.apply(menuItem);
            return   menuItemService.update(target);
        }catch(MenuItemException e){
            throw new WebClientSideException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @BeforeFilter
    protected void initTargetMenu(HttpServletRequest request){

        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);

        if(StringUtils.isNotBlank(finalPath)){
            this.target = menuItemService.findByPath(finalPath);
        }else{
            this.target = null;
        }
    }
}
