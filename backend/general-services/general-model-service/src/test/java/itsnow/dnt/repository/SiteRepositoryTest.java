package itsnow.dnt.repository;

import dnt.itsnow.model.Dictionary;
import dnt.itsnow.model.Site;
import dnt.itsnow.model.WorkTime;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.DictionaryRepository;
import dnt.itsnow.repository.SiteDeptRepository;
import dnt.itsnow.repository.SiteRepository;
import dnt.itsnow.repository.WorkTimeRepository;
import itsnow.dnt.config.SiteRepositoryConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>测试SiteRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = SiteRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class SiteRepositoryTest {

    @Autowired
    SiteRepository repository;

    @Autowired
    DictionaryRepository dictionaryRepository;

    @Autowired
    WorkTimeRepository workTimeRepository;

    @Autowired
    SiteDeptRepository siteDeptRepository;

    PageRequest pageRequest;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testCreate() throws Exception {
        Site site = new Site();
        site.setSn("100");
        site.setName("大众四厂");

        Dictionary dictionary = dictionaryRepository.findBySn("001");
        site.setDictionary(dictionary);

        WorkTime workTime = workTimeRepository.findBySn("plan1");
        site.setWorkTime(workTime);

        site.setDescription("It's test.");
        site.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        site.setUpdatedAt(site.getCreatedAt());
        repository.create(site);

        Assert.assertNotNull(site.getId());
    }

    @Test
    public void testDelete() throws Exception {
        String sn = "004";
        Site site = repository.findBySn(sn);
        Assert.assertNotNull(site);

        siteDeptRepository.deleteSiteAndDeptRelationBySiteId(site.getId());

        repository.delete(sn);
        Assert.assertNull(repository.findBySn(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "003";
        Site site = repository.findBySn(sn);
        site.setAddress("Hello World!");
        repository.update(site);
        Assert.assertEquals("Hello World!", site.getAddress());
    }

    @Test
    public void testCount() throws Exception {
        Assert.assertNotNull(repository.count(""));
    }

    @Test
    public void testFind() throws Exception {
        List<Site> sites = repository.findAll("", pageRequest);
        Assert.assertNotNull(sites);
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(repository.findBySn("001"));
        Assert.assertNull(repository.findBySn("800"));
    }

}
