package dnt.itsnow.repository;

import dnt.itsnow.config.MutableContractRepositoryConfig;
import dnt.itsnow.model.Contract;
import dnt.itsnow.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>测试MutableContractRepository的Mybatis的Mapping配置是否正确</h1>
 */
@ContextConfiguration(classes = MutableContractRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MutableContractRepositoryTest {

    @Autowired
    MutableContractRepository repository;

    Contract contract;

    @Before
    public void setup() {
        contract = new Contract();
        contract.setId(1L);

        User user = new User();
        user.setId(1L);
        List<User> users = new ArrayList<User>();
        users.add(user);

        contract.setUsers(users);
    }

    @Test
    public void testFindRelation() throws Exception {
        repository.findRelation(3L, 2L);
    }

    @Test
    public void testBuildRelation() throws Exception {
        repository.buildRelation(8L,2L);
    }

    @Test
    public void testDeleteRelation() throws Exception {
        repository.deleteRelation(2L);
    }

}
