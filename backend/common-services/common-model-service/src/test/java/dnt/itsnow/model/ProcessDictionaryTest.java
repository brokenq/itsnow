package dnt.itsnow.model;

import dnt.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.util.Set;

/**
 * <h1>流程字典测试类</h1>
 */
public class ProcessDictionaryTest {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    private ProcessDictionary dictionary;

    @Before
    public void setUp() throws Exception {
        dictionary = new ProcessDictionary();
        dictionary.setId(10L);
        dictionary.setSn("010");
        dictionary.setCode("inc010");
        dictionary.setName("优先级");
        dictionary.setDisplay("高");
        dictionary.setVal("high");
        dictionary.setState("1");
        dictionary.setType("1");
        dictionary.setDescription("This is a test.");
        dictionary.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        dictionary.setUpdatedAt(dictionary.getCreatedAt());
    }

    @Test
    @Ignore
    public void testDictionaryIsNotValid() throws Exception {
        dictionary.setSn(null);
        Set<ConstraintViolation<ProcessDictionary>> violations = validator.validate(dictionary);
        Assert.assertFalse(violations == null || violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(dictionary);
        System.out.println(json);
        ProcessDictionary parsed = JsonSupport.parseJson(json, ProcessDictionary.class);
        Assert.assertEquals(ProcessDictionary.class, parsed.getClass());
    }

}
