package itsnow.repository;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.model.PublicServiceItem;
import dnt.itsnow.repository.PublicServiceCatalogRepository;
import dnt.itsnow.repository.PublicServiceItemRepository;
import itsnow.config.MutableServiceCatalogRepositoryConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>测试MutableServiceItemRepository的Mybatis的Mapping配置是否正确</h1>
 *
 */
@ContextConfiguration(classes = MutableServiceCatalogRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MutableServiceItemRepositoryTest {
    @Autowired
    PublicServiceItemRepository repository;

    @Autowired
    PublicServiceCatalogRepository catalogRepository;

    @Test
    public void testFindBySn() throws Exception {
        PublicServiceItem item = repository.findBySn("sn-1011");
        validateItem(item);
        Assert.isNull(repository.findBySn("Not Exist"));
    }

    @Test
    public void testCreate() throws Exception {
        PublicServiceItem item = new PublicServiceItem();
        PublicServiceCatalog catalog = catalogRepository.findBySn("SC_100");
        item.setCatalog(catalog);
        item.setSn("SC_TEST");
        item.setTitle("title_test");
        item.setDescription("desc_test");
        item.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        item.setUpdatedAt(item.getCreatedAt());
        repository.save(item);
        Assert.notNull(item.getId());
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
