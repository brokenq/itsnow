/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.support;

import dnt.itsnow.annotation.Dictionary;
import dnt.itsnow.config.DictionaryManagerConfig;
import dnt.itsnow.model.DictDetail;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.service.DictionaryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <h1>Class Usage</h1>
 */
@ContextConfiguration(classes = DictionaryManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryManagerTest {

    PageRequest pageRequest;

    dnt.itsnow.model.Dictionary dictionary;

    @Autowired
    DictionaryService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
        dictionary = new dnt.itsnow.model.Dictionary();
        DictDetail detail11 = new DictDetail();
        detail11.setKey("key");
        detail11.setValue("value");

        DictDetail detail22 = new DictDetail();
        detail22.setKey("key2");
        detail22.setValue("value2");

        DictDetail[] detailList = new DictDetail[]{detail11, detail22};
        dictionary.setCode("inc007");
        dictionary.setName("Test Account");
        dictionary.setLabel("北京");
        dictionary.setDetails(detailList);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<dnt.itsnow.model.Dictionary> dictionaries = service.findAll("", pageRequest);
        Assert.assertNotNull(dictionaries.getTotalElements());
        Assert.assertNotNull(dictionaries.getNumberOfElements());
    }

    @Test
    public void testFindByCode() throws Exception {
        Assert.assertNotNull(service.findByCode("002"));
        Assert.assertNull(service.findByCode("0003"));
    }

    @Test
    public void testFind() throws Exception {
        Assert.assertNotNull(service.find(DictAnnotation.class));
    }

    @Test
    public void testCreate() throws Exception {
        service.create(dictionary);
        Assert.assertNotNull(dictionary.getDetails());
    }

    @Test
    public void testUpdate() throws Exception {
        String code = "001";
        dnt.itsnow.model.Dictionary dictionary = service.findByCode(code);
        dictionary.setLabel("深圳");

        DictDetail detail = new DictDetail();
        detail.setKey("key22");
        detail.setValue("value22");
        DictDetail[] detailList = new DictDetail[]{detail};
        dictionary.setDetails(detailList);

        service.update(dictionary);
        dictionary = service.findByCode(code);
        Assert.assertTrue(dictionary.getLabel().equals("深圳"));
    }

    @Test
    public void testDestroy() throws Exception {
        String code = "003";
        dnt.itsnow.model.Dictionary dictionary = service.findByCode(code);
        Assert.assertNotNull(dictionary);
        service.destroy(dictionary);
        Assert.assertNull(service.findByCode(code));
    }

    class DictAnnotation {
        @Dictionary(code = "001")
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
