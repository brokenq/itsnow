package dnt.itsnow.it;

//import dnt.itsnow.model.Incident;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * Created by jacky on 2014/9/16.
 */
public class MsuIncidentTest extends AbstractTest {

    @Override
    protected void configure(Configuration configuration) {
        configuration
                .host("localhost")
                .port(8072)
                .username("jacky.cao")
                .password("secret");
    }

//    @Test
//    public void testFindAllOpenedIncidents() throws Exception {
//        List<Incident> incidents = withLoginUser(new Callback<List<Incident>>() {
//            @Override
//            public List<Incident> perform(HttpHeaders headers) {
//                HttpEntity request = new HttpEntity(headers);
//                return getForObject("/api/msu-incidents", List.class, request);
//            }
//        });
//        Assert.assertNotNull(incidents);
//    }

}
