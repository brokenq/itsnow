/**
 * Developer: Kadvin Date: 14-9-4 下午1:44
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import net.happyonroad.support.JsonSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Test the Contract
 */
public class ContractTest extends ValidatorSupport{
    Contract contract;

    @Before
    public void setUp() throws Exception {
        contract = new Contract();
        contract.setMspAccountId(2L);
        contract.setStatus(ContractStatus.Approved);
        contract.setMsuAccountId(4L);
        contract.setSn("CONTRACT-001");
        contract.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        contract.setId(1L);
        contract.isApproved();
    }

    @Test
    public void testValidationHappyCase() throws Exception {
        Set<ConstraintViolation<Contract>> violations = validator.validate(contract);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(contract);
        System.out.println(json);
        Contract parsed = JsonSupport.parseJson(json, Contract.class);
        Assert.assertEquals(contract, parsed);
    }

}
