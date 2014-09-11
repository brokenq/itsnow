/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.ProcessDictionaryRepositoryConfig;
import dnt.itsnow.model.ProcessDictionary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * <h1>测试ProcessDictionaryRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = ProcessDictionaryRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProcessDictionaryRepositoryTest {

    @Autowired
    ProcessDictionaryRepository repository;

    @Test
    public void testCreate() throws Exception {
        ProcessDictionary dictionary = new ProcessDictionary();
        dictionary.setCode("inc003");
        dictionary.setName("影响范围");
        dictionary.setLevel("high");
        dictionary.setLevelName("高");
        dictionary.setState("1");
        repository.create(dictionary);
        Assert.notNull(dictionary.getId());
    }

    @Test
    public void testDelete() throws Exception {
        String sn = "inc001";
        Assert.notNull(repository.findByCode(sn));
        repository.delete(sn);
        Assert.isNull(repository.findByCode(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "inc002";
        ProcessDictionary dictionary = repository.findByCode(sn);
        dictionary.setState("0");
        repository.update(dictionary);
        dictionary = repository.findByCode(sn);
        Assert.isTrue(dictionary.getState() == "0");
    }

    @Test
    public void testCount() throws Exception {
        Assert.notNull(repository.count());
    }

    @Test
    public void testFind() throws Exception {
        Assert.notNull(repository.find("updated_at", "desc",  0, 10));
    }

    @Test
    public void testCountByKeyword() throws Exception {
        Assert.notNull(repository.countByKeyword("%003%"));
    }

    @Test
    public void testFindByKeyword() throws Exception {
        Assert.notNull(repository.findByKeyword("%003%","updated_at","desc", 0, 10));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.notNull(repository.findByCode("inc002"));
        Assert.isNull(repository.findByCode("inc0002"));
    }

}
