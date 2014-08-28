package dnt.itsnow.service;

import dnt.itsnow.exception.MenuItemException;
import dnt.itsnow.model.MenuItem;

import java.util.List;

/**
 * <h1>菜单服务类</h1>
 */
public interface MenuItemService {

    /**
     * 创建菜单
     *
     * @param parent
     * @param menuItem
     * @return MenuItem
     * @throws MenuItemException
     */
    public MenuItem create(MenuItem parent, MenuItem menuItem) throws MenuItemException;

    /**
     * 删除菜单（逻辑删除）
     * @param id
     * @throws MenuItemException
     */
    public void destroy(Long id) throws MenuItemException;

    /**
     * 修改菜单
     * @param menuItem
     * @return MenuItem
     * @throws MenuItemException
     */
    public MenuItem update(MenuItem menuItem) throws MenuItemException;

    /**
     * 查询菜单
     * @param id the menu id
     * @return MenuItem
     * @throws MenuItemException
     */
    public MenuItem find(Long id) throws MenuItemException;

    /**
     * 加载所有菜单
     * @return List<MenuItem>
     * @param tree return as tree structure
     */
    public List<MenuItem> findAll(boolean tree);

    MenuItem findByPath(String path);

    List<MenuItem> findAllByParent(MenuItem parent, boolean tree);
}
