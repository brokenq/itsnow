package dnt.itsnow.support;

import dnt.itsnow.exception.MenuItemException;
import dnt.itsnow.model.MenuItem;
import dnt.itsnow.repository.CommonMenuItemRepository;
import dnt.itsnow.service.CommonMenuItemService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sin on 2014/8/25.
 */
@Service
public class CommonMenuItemManager extends Bean implements CommonMenuItemService {

    @Autowired
    private CommonMenuItemRepository repository;

    @Override
    public void create(MenuItem menuItem) throws MenuItemException {
        logger.info("Creating menuItem {}", menuItem);
        if(menuItem==null){
            throw new MenuItemException("保存的菜单，不能为空！");
        }
        repository.save(menuItem);
    }

    @Override
    public void destroy(MenuItem menuItem) throws MenuItemException {
        logger.warn("Deleting menuItem {}", menuItem);
        if(menuItem==null){
            throw new MenuItemException("你所销毁的菜单，不能为空！");
        }
        repository.update(menuItem);
    }

    @Override
    public void update(MenuItem menuItem) throws MenuItemException {
        logger.info("Updating menuItem {}", menuItem);
        if(menuItem==null){
            throw new MenuItemException("你提交的菜单内容，不能为空！");
        }
        repository.update(menuItem);
    }

    @Override
    public MenuItem find(Long id) throws MenuItemException {
        logger.debug("Finding menuItem by id {}", id);
        if(id==null){
            throw new MenuItemException("你提交的菜单ID，不能为空！");
        }
        return repository.findById(id);
    }

//    @Override
//    public List<MenuItem> findAll() {
//        logger.debug("Finding all of menuItem");
//        List<MenuItem> mainMenuItemList = repository.findByMainMenu();
//        for(MenuItem mainMenuItem : mainMenuItemList){
//            List<MenuItem> subMenuItemList =repository.findBySubMenu(mainMenuItem.getId());
//            if(subMenuItemList!=null && !subMenuItemList.isEmpty()){
//                for(MenuItem subMenuItem : subMenuItemList){
//                    mainMenuItem.addSubMenuItem(subMenuItem);
//                }
//            }
//        }
//        return mainMenuItemList;
//    }

    @Override
    public List<MenuItem> findAll() {
        logger.debug("Finding all of menuItem");
        List<MenuItem> mainMenuItemList = repository.findByMainMenu();
        this.getMenuItemTree(mainMenuItemList);
        return mainMenuItemList;
    }

    /**
     * 创建菜单树
     * @param menuItemList
     * @return
     */
    public List<MenuItem> getMenuItemTree(List<MenuItem> menuItemList){
        for(MenuItem menuItem : menuItemList){
            List<MenuItem> subMenuItemList =repository.findBySubMenu(menuItem.getId());
            if(subMenuItemList!=null && !subMenuItemList.isEmpty()){
                for(MenuItem subMenuItem : subMenuItemList){
                    menuItem.addSubMenuItem(subMenuItem);
                }
                this.getMenuItemTree(subMenuItemList);
            }
        }
        return menuItemList;
    }
}
