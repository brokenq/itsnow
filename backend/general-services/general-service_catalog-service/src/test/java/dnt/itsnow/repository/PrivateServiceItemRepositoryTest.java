package dnt.itsnow.repository;

import dnt.itsnow.config.PrivateServiceCatalogRepositoryConfig;
import dnt.itsnow.model.PrivateServiceCatalog;
import dnt.itsnow.model.PrivateServiceItem;
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
 * <h1>测试PrivateServiceItemRepository的Mybatis的Mapping配置是否正确</h1>
 *
 */
@ContextConfiguration(classes = PrivateServiceCatalogRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PrivateServiceItemRepositoryTest {
    @Autowired
    PrivateServiceItemRepository repository;

    @Autowired
    PrivateServiceCatalogRepository catalogRepository;

    @Test
    public void testFindBySn() throws Exception {
        PrivateServiceItem item = repository.findPrivateBySn("sn-1011");
        validateItem(item);
        Assert.isNull(repository.findPrivateBySn("Not Exist"));
    }

    @Test
    public void testCreate() throws Exception {
        PrivateServiceItem item = new PrivateServiceItem();
        PrivateServiceCatalog catalog = catalogRepository.findPrivateBySn("SC_100");
        item.setCatalog(catalog);
        item.setSn("SC_TEST");
        item.setTitle("title_test");
        item.setDescription("desc_test");
        item.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        item.setUpdatedAt(item.getCreatedAt());
        repository.savePrivate(item);
        //item.setId(1L);
        Assert.notNull(item.getId());
    }


    @Test
    public void testFindAll() throws Exception {
        List<PrivateServiceItem> list = repository.findAllPrivate();
        Assert.isTrue(list.size() > 0);
    }

    protected void validateItem(PrivateServiceItem item) {
        Assert.notNull(item);
        Assert.notNull(item.getId());
        Assert.notNull(item.getSn());
        Assert.notNull(item.getTitle());
        Assert.notNull(item.getCatalog());
    }
}
