package dnt.itsnow.repository;

import dnt.itsnow.config.CommonServiceCatalogRepositoryConfig;
import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.PublicServiceItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <h1>测试CommonServiceItemRepository的Mybatis的Mapping配置是否正确</h1>
 *
 */
@ContextConfiguration(classes = CommonServiceCatalogRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonServiceItemRepositoryTest {
    @Autowired
    CommonServiceItemRepository repository;

    @Test
    public void testFindBySn() throws Exception {
        PublicServiceItem item = repository.findBySn("sn-1011");
        validateItem(item);
        Assert.isNull(repository.findBySn("Not Exist"));
    }


    @Test
    public void testFindAll() throws Exception {
        List<PublicServiceItem> list = repository.findAll();
        Assert.isTrue(list.size() > 0);
    }

    protected void validateItem(PublicServiceItem item) {
        Assert.notNull(item);
        Assert.notNull(item.getId());
        Assert.notNull(item.getSn());
        Assert.notNull(item.getTitle());
        Assert.notNull(item.getCatalog());
    }
}
