package dnt.itsnow.repository;

import dnt.itsnow.config.MutableContractRepositoryConfig;
import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractUser;
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

    ContractUser contractUser;

    @Before
    public void setup() {
        Contract contract = new Contract();
        contract.setId(1L);
        User user = new User();
        user.setId(1L);
        List<User> users = new ArrayList<User>();
        users.add(user);
        contractUser = new ContractUser();
        contractUser.setContract(contract);
        contractUser.setUsers(users);
    }

    @Test
    public void testBuildRelation() throws Exception {
        repository.buildRelation(contractUser);
    }

    @Test
    public void testUpdateRelation() throws Exception {
        repository.buildRelation(contractUser);
        contractUser.setAccess("0");
        repository.updateRelation(contractUser);
    }

}
