package dnt.itsnow.model;

import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.util.Set;

/**
 * <h1>MenuItem测试类</h1>
 */
public class ProcessDictionaryTest {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    private ProcessDictionary dictionary;

    @Before
    public void setUp() throws Exception {
        dictionary = new ProcessDictionary();
        this.dictionary.setId(1L);
        this.dictionary.setName("用户");
        this.dictionary.setState("index.user");
        this.dictionary.setDescription("This is a test.");
        this.dictionary.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        this.dictionary.setUpdatedAt(this.dictionary.getCreatedAt());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(dictionary);
        System.out.println(json);
        Assert.assertNotNull(json);
    }

}
