/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DictionaryRepositoryConfig;
import dnt.itsnow.model.DictDetail;
import dnt.itsnow.model.Dictionary;
import net.happyonroad.platform.util.PageRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;

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

        DictDetail detail = new DictDetail();
        detail.setKey("key");
        detail.setValue("value");
        DictDetail[] detailList = new DictDetail[]{detail};

        dictionary.setCode("004");
        dictionary.setName("影响范围");
        dictionary.setDetails(detailList);
        repository.create(dictionary);
        Assert.notNull(dictionary.getDetails());
    }


    @Test
    public void testUpdate() throws Exception {

        String code = "001";
        Dictionary dictionary = repository.findByCode(code);
        DictDetail detail = new DictDetail();
        detail.setKey("key");
        detail.setValue("value");
        DictDetail[] detailList = new DictDetail[]{detail};
        dictionary.setLabel("深圳");
        dictionary.setDetails(detailList);

        repository.update(dictionary);
        dictionary = repository.findByCode(code);
        Assert.isTrue(dictionary.getLabel() == "深圳");

//        String sn = "002";
//        Dictionary dictionary = repository.findBySn(sn);
//        dictionary.setState("0");
//        repository.update(dictionary);
//        dictionary = repository.findBySn(sn);
//        Assert.isTrue(dictionary.getState() == "0");
    }
//
    @Test
    public void testDelete() throws Exception {
        String code = "003";
        Assert.notNull(repository.findByCode(code));
        repository.delete(code);
        Assert.isNull(repository.findByCode(code));
    }

    @Test
    public void testCount() throws Exception {
        Assert.notNull(repository.count(""));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Dictionary> dicts = repository.findAll("", pageRequest);
        Assert.notNull(dicts);
    }
//
    @Test
    public void testFindByCode() throws Exception {
        Assert.notNull(repository.findByCode("002"));
        Assert.isNull(repository.findByCode("0002"));
    }

}
