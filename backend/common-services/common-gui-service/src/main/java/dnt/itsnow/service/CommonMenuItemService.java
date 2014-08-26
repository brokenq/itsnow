package dnt.itsnow.service;

import dnt.itsnow.exception.MenuItemException;
import dnt.itsnow.model.MenuItem;

import java.util.List;

/**
 * Created by Sin on 2014/8/22.
 * 菜单服务类
 */
public interface CommonMenuItemService {

    /**
     * 创建菜单
     * @param menuItem
     * @throws MenuItemException
     */
    void create(MenuItem menuItem) throws MenuItemException;

    /**
     * 删除菜单（逻辑删除）
     * @param menuItem
     * @throws MenuItemException
     */
    void destroy(MenuItem menuItem) throws MenuItemException;

    /**
     * 修改菜单
     * @param menuItem
     * @throws MenuItemException
     */
    void update(MenuItem menuItem) throws MenuItemException;

    /**
     * 查询菜单
     * @param id
     * @return
     * @throws MenuItemException
     */
    MenuItem find(Long id) throws MenuItemException;

    /**
     * 加载所有菜单
     * @return
     */
    List<MenuItem> findAll();

}
