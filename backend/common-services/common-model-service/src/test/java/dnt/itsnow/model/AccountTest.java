/**
 * xiongjie on 14-8-7.
 */
package dnt.itsnow.model;

import dnt.itsnow.test.model.ValidatorSupport;
import net.happyonroad.support.JsonSupport;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert;

import javax.validation.ConstraintViolation;
import java.sql.Timestamp;
import java.util.Set;

/**
 * <h1>账户模型的测试</h1>
 *
 * 这种模型测试主要包括两大方面：
 * <ul>
 * <li>模型完整性</li>
 * <li>JSON序列化</li>
 * <li>业务方法</li>
 * </ul>
 */
public class AccountTest extends ValidatorSupport{

    Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account();
        this.account.setName("demo");
        this.account.setDomain("demo");
        this.account.setSn("msu-099");
        this.account.setId(1L);
        this.account.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.account.setUpdatedAt(this.account.getCreatedAt());
    }

    @Test
    @Ignore
    public void testSnConstraints() throws Exception {
        account.setSn(null);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testNameConstraints() throws Exception {
        account.setName(null);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        Assert.assertFalse(violations.isEmpty());

        account.setName("hel");
        violations = validator.validate(account);
        Assert.assertFalse(violations.isEmpty());

        account.setName( "very_very_very_very_very_very_very_very_very_very_very_very_very_very_very_very_long_name");
        violations = validator.validate(account);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testDomainConstraints() throws Exception{
        account.setDomain("invalid.domain");
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    public void testReservedDomain() throws Exception {
        for(String kept : Account.RESERVED_DOMAINS){
            account.setDomain(kept);
            Set<ConstraintViolation<Account>> violations = validator.validate(account);
            Assert.assertFalse(violations.isEmpty());
        }
    }

    @Test
    public void testCorrectAccountIsValid() throws Exception {
        account.setSn("msu-001");
        account.setName("Test");
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        Assert.assertTrue(violations == null || violations.isEmpty());
    }

    @Test
    public void testApply() throws Exception {
        Account another = new Account();
        another.apply(account);
        Assert.assertEquals(account, another);
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(account);
        System.out.println(json);
        Account parsed = JsonSupport.parseJson(json, Account.class);
        Assert.assertEquals(account, parsed);
    }
}
