package dnt.itsnow.support;

import dnt.itsnow.config.CommonMenuItemManagerConfig;
import dnt.itsnow.model.MenuItem;
import dnt.itsnow.service.CommonMenuItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Sin on 2014/8/26.
 */
@ContextConfiguration(classes = CommonMenuItemManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonMenuItemManagerTest {

    @Autowired
    CommonMenuItemService service;

    @Test
    public void testFindById() throws Exception {
        Assert.notNull(service.find(1L));
    }

    @Test
    public void testFindAll() throws Exception {
        List<MenuItem> menuItemList = service.findAll();
        System.out.print(menuItemList);
        Assert.notNull(menuItemList);
    }

}
