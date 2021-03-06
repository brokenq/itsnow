/**
 * xiongjie on 14-8-6.
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.MsuIncidentRepositoryConfig;
import dnt.itsnow.model.Incident;
import dnt.itsnow.platform.util.PageRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>测试MsuIncidentRepository的Mybatis的Mapping配置是否正确</h1>
 *
 */
@ContextConfiguration(classes = MsuIncidentRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MsuIncidentRepositoryTest {
    @Autowired
    MsuIncidentRepository repository;

    @Test
    public void testFindByInstanceId() throws Exception {
        Assert.notNull(repository.findByInstanceId("1"));
        Assert.isNull(repository.findByInstanceId("12345"));
    }

    @Test
    public void testFindAllByInstanceIds() throws Exception {
        Set<String> ids = new HashSet<String>();
        ids.add("1");
        ids.add("2");
        PageRequest pageRequest = new PageRequest(0, 10, null);
        Assert.isTrue(repository.findAllByInstanceIds(ids,"",pageRequest).size()>0);
    }

    @Test
    public void testCreate() throws Exception {
        Incident incident = new Incident();
        incident.setRequestDescription("desc");
        incident.setRequesterName("name");
        incident.setNumber("INC001234");
        incident.setCreatedBy("fff");
        repository.create(incident);
        Assert.notNull(incident.getId());
    }
}
