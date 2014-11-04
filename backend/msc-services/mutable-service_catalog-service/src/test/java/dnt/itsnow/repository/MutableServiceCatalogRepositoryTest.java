package dnt.itsnow.repository;

import dnt.itsnow.config.MutableServiceCatalogRepositoryConfig;
import dnt.itsnow.model.PublicServiceCatalog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <h1>测试CommonServiceCatalogRepository的Mybatis的Mapping配置是否正确</h1>
 *
 */
@ContextConfiguration(classes = MutableServiceCatalogRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MutableServiceCatalogRepositoryTest {
    @Autowired
    PublicServiceCatalogRepository repository;

    @Test
    public void testFindBySn() throws Exception {
        PublicServiceCatalog catalog = repository.findBySn("SC_100");
        validateCatalog(catalog);
        Assert.isNull(repository.findBySn("Not Exist"));
    }


    @Test
    public void testFindAll() throws Exception {
        List<PublicServiceCatalog> list = repository.findAll();
        Assert.isTrue(list.size() > 0);
    }

    protected void validateCatalog(PublicServiceCatalog catalog) {
        Assert.notNull(catalog);
        Assert.notNull(catalog.getId());
        Assert.notNull(catalog.getSn());
    }
}
