package dnt.itsnow.repository;

import dnt.itsnow.config.MutableContractRepositoryConfig;
import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractMspAccount;
import dnt.itsnow.model.User;
import net.happyonroad.platform.util.PageRequest;
import org.junit.Assert;
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
    PageRequest pageRequest;
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

        pageRequest = new PageRequest(0, 1);
    }

    @Test
    public void testBuildRelation() throws Exception {
        repository.buildRelation(2L, 3L, "1");
    }

    @Test
    public void testDeleteRelation() throws Exception {
        repository.deleteRelation(3L);
    }

    @Test
    public void testCountByMsuAccountId() {
        Assert.assertTrue(repository.countByMsuAccountId(2L) > 0);
    }

    @Test
    public void testCountByMspAccountId() {
        Assert.assertTrue(repository.countByMspAccountId(3L) > 0);
    }

    @Test
    public void testFindAll() {
        Assert.assertTrue(repository.findAll(pageRequest).size() > 0);
    }

    @Test
    public void testFindAllByMsuAccountId() {
        List contracts = repository.findAllByMsuAccountId(2L, pageRequest);
        Assert.assertTrue(contracts.size() > 0);
    }

    @Test
    public void testFindAllByMspDraft() {
        Assert.assertTrue(repository.findAllByMspDraft(3L, pageRequest).size() > 0);
    }

    @Test
    public void testFindAllByMspAccountId() {
        Assert.assertTrue(repository.findAllByMspAccountId(3L, pageRequest).size() > 0);
    }

    @Test
    public void findMspAccountById() {
        List<ContractMspAccount> accounts = repository.findMspAccountById(2L);
        Assert.assertTrue(accounts.size() > 0);
    }

}
