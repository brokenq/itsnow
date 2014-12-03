package dnt.itsnow.support;

import dnt.itsnow.config.CommonServiceCatalogManagerConfig;
import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.ServiceCatalog;
import dnt.itsnow.service.CommonServiceCatalogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <h1>Class Usage</h1>
 */
@ContextConfiguration(classes = CommonServiceCatalogManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonServiceCatalogManagerTest {
    @Autowired
    CommonServiceCatalogService service;

    @Test
    public void testFindAll() throws Exception {
        List<PublicServiceCatalog> list = service.findAll();
        Assert.isTrue(list!=null&&list.size()>0);
    }
    @Test
    public void testfindCatalogsBySn() throws Exception {
        List<ServiceCatalog> list = service.findCatalogsBySn("SC_100");
        System.out.print(list);
        Assert.isTrue(list!=null&&list.size()>0);
    }

}
