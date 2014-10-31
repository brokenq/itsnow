package itsnow.repository;

import dnt.itsnow.config.PrivateServiceCatalogRepositoryConfig;
import dnt.itsnow.model.PrivateServiceCatalog;
import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.repository.PrivateServiceCatalogRepository;
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
@ContextConfiguration(classes = PrivateServiceCatalogRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class PrivateServiceCatalogRepositoryTest {
    @Autowired
    PrivateServiceCatalogRepository repository;

    @Test
    public void testFindBySn() throws Exception {
        PrivateServiceCatalog catalog =  repository.findPrivateBySn("SC_100");
        validateCatalog(catalog);
        Assert.isNull(repository.findPrivateBySn("Not Exist"));
    }


    @Test
    public void testFindAll() throws Exception {
        List<PrivateServiceCatalog> list = repository.findAllPrivate();
        Assert.isTrue(list.size() > 0);
    }

    protected void validateCatalog(PrivateServiceCatalog catalog) {
        Assert.notNull(catalog);
        Assert.notNull(catalog.getId());
        Assert.notNull(catalog.getSn());
    }
}
