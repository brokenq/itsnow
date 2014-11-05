/**
 * Developer: Kadvin Date: 14/11/4 下午9:15
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.SequenceRepositoryConfig;
import dnt.itsnow.model.Sequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * <h1>测试SequenceRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = SequenceRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class SequenceRepositoryTest {
    @Autowired
    SequenceRepository repository;

    @Test
    public void testFindAll() throws Exception {
        List<Sequence> all = repository.findAll();
        Assert.assertEquals(3, all.size());
    }

    @Test
    public void testFindByCatalog() throws Exception {
        Sequence sequence = repository.findByCatalog("TEST");
        Assert.assertEquals("TEST", sequence.getCatalog());
        Assert.assertEquals("TEST_%06d@1000", sequence.getRule());
        Assert.assertEquals(1100L, sequence.getValue());
        Assert.assertEquals(1, sequence.getIncrement());
    }

    @Test
    public void testUpdate() throws Exception {
        Sequence sequence = repository.findByCatalog("BASE");
        sequence.setRule("base_%08d@1000");
        sequence.setIncrement(12);
        repository.update(sequence);
        sequence = repository.findByCatalog("BASE");
        Assert.assertEquals("base_%08d@1000", sequence.getRule());
        // the value can't be updated by this interface
        Assert.assertEquals(1010, sequence.getValue());
        Assert.assertEquals(12, sequence.getIncrement());
    }

    @Test
    public void testCreate() throws Exception {
        Sequence sequence = new Sequence("New", "New_%d");
        sequence.setValue(100);
        sequence.setIncrement(10);
        repository.create(sequence);
        Sequence found = repository.findByCatalog("New");
        Assert.assertEquals("New_%d", found.getRule());
        Assert.assertEquals(100, found.getValue());
        Assert.assertEquals(10, found.getIncrement());
        repository.deleteByCatalog("New");
    }

    // function not supported by h2
    @Test
    public void testNextValue() throws Exception {

    }

    @Test
    public void testCurrValue() throws Exception {

    }

    @Test
    public void testSetValue() throws Exception {

    }
}
