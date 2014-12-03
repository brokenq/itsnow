package dnt.itsnow.model;

import net.happyonroad.support.JsonSupport;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <h1>流程字典测试类</h1>
 */
public class DictionaryTest {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static Validator validator = factory.getValidator();

    private Dictionary dictionary;

    @Before
    public void setUp() throws Exception {
        dictionary = new Dictionary();
        List<DictDetail> list = new ArrayList<DictDetail>();

        DictDetail detail = new DictDetail();
        detail.setKey("key");
        detail.setValue("value");
        DictDetail[] detailList = new DictDetail[]{detail};

        dictionary.setId(10L);
        dictionary.setCode("inc010");
        dictionary.setName("优先级");
        dictionary.setLabel("高");
        dictionary.setDescription("This is a test.");
        dictionary.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        dictionary.setUpdatedAt(dictionary.getCreatedAt());
        dictionary.setDetails(detailList);
    }

    @Test
    public void testDictionaryIsNotValid() throws Exception {
        dictionary.setCode(null);
        Set<ConstraintViolation<Dictionary>> violations = validator.validate(dictionary);
        Assert.assertFalse(violations == null || violations.isEmpty());
    }

    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(dictionary);
        System.out.println(json);
        Dictionary parsed = JsonSupport.parseJson(json, Dictionary.class);
        Assert.assertEquals(Dictionary.class, parsed.getClass());
    }

}
