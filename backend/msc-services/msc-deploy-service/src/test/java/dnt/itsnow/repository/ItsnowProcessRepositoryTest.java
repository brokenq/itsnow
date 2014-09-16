/**
 * Developer: Kadvin Date: 14-9-15 下午3:23
 */
package dnt.itsnow.repository;

import dnt.itsnow.config.DeployRepositoryConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 Itsnow Process Repository
 */
@ContextConfiguration(classes = DeployRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class ItsnowProcessRepositoryTest {

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testDeleteByName() throws Exception {

    }

    @Test
    public void testCountByKeyword() throws Exception {

    }

    @Test
    public void testFindAllByKeyword() throws Exception {


    }
}
