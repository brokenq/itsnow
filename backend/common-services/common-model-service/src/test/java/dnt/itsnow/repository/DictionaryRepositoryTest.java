/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DictionaryRepositoryConfig;
import dnt.itsnow.model.Dictionary;
import dnt.itsnow.platform.util.PageRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * <h1>测试DictionaryRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = DictionaryRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryRepositoryTest {

    @Autowired
    DictionaryRepository repository;

    PageRequest pageRequest;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testCreate() throws Exception {
        Dictionary dictionary = new Dictionary();
        dictionary.setCode("004");
        dictionary.setName("影响范围");
        dictionary.setDisplay("高");
        dictionary.setVal("high");
        dictionary.setState("1");
        repository.create(dictionary);
        Assert.notNull(dictionary.getId());
    }

    @Test
    public void testDelete() throws Exception {
        String sn = "001";
        Assert.notNull(repository.findBySn(sn));
        repository.delete(sn);
        Assert.isNull(repository.findBySn(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "002";
        Dictionary dictionary = repository.findBySn(sn);
        dictionary.setState("0");
        repository.update(dictionary);
        dictionary = repository.findBySn(sn);
        Assert.isTrue(dictionary.getState() == "0");
    }

    @Test
    public void testCount() throws Exception {
        Assert.notNull(repository.count(""));
    }

    @Test
    public void testFindAll() throws Exception {
        Assert.notNull(repository.findAll("", pageRequest));
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.notNull(repository.findBySn("002"));
        Assert.isNull(repository.findBySn("0002"));
    }

}
