/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.support;

import dnt.itsnow.config.ProcessDictionaryManagerConfig;
import dnt.itsnow.model.ProcessDictionary;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.ProcessDictionaryService;
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
@ContextConfiguration(classes = ProcessDictionaryManagerConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProcessDictionaryManagerTest {

    PageRequest pageRequest;

    ProcessDictionary dictionary;

    @Autowired
    ProcessDictionaryService service;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
        dictionary = new ProcessDictionary();
        dictionary.setCode("inc007");
        dictionary.setName("Test Account");
    }

    @Test
    public void testFindAll() throws Exception {
        Page<ProcessDictionary> dictionaries = service.findAll("003", pageRequest);
        Assert.assertNotNull(dictionaries.getTotalElements());
        Assert.assertNotNull(dictionaries.getNumberOfElements());
    }

    @Test
    public void testFindBySn() throws Exception {
        Assert.assertNotNull(service.findBySn("002"));
        Assert.assertNull(service.findBySn("0003"));
    }

    @Test
    public void testFindByCode() throws Exception {
        Assert.assertNotNull(service.findByCode("inc002"));
        Assert.assertTrue(service.findByCode("asdf").size()==0);
    }

    @Test
    public void testCreate() throws Exception {
        ProcessDictionary dictionary = new ProcessDictionary();
        dictionary.setCode("inc003");
        dictionary.setName("影响范围");
        dictionary.setDisplay("高");
        dictionary.setVal("high");
        dictionary.setState("1");
        service.create(dictionary);
        Assert.assertNotNull(dictionary.getId());
    }

    @Test
    public void testDestroy() throws Exception {
        String sn = "001";
        ProcessDictionary dictionary = service.findBySn(sn);
        Assert.assertNotNull(dictionary);
        service.destroy(dictionary);
        Assert.assertNull(service.findBySn(sn));
    }

    @Test
    public void testUpdate() throws Exception {
        String sn = "002";
        ProcessDictionary dictionary = service.findBySn(sn);
        dictionary.setState("0");
        service.update(dictionary);
        dictionary = service.findBySn(sn);
        Assert.assertTrue(dictionary.getState() == "0");
    }

}
