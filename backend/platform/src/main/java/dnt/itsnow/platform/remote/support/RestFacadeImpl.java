/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.platform.remote.support;

import dnt.itsnow.platform.remote.service.RestFacade;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * <h1>基于 Spring Rest Template 实现的 Rest Facade</h1>
 */
@Component
public class RestFacadeImpl implements RestFacade {

    private RestTemplate template = new RestTemplate();
    String prefix ;

    public RestFacadeImpl() {
        String mscHost = System.getProperty("msc.host", "localhost");
        String mscPort = System.getProperty("msc.port", "8071");
        prefix = "http://" + mscHost + ":" + mscPort + "/";
    }

    @Override
    public void put(String url, Object... pathVariables) {
        template.put(prefix + url, null, pathVariables);
    }

    @Override
    public <T> T putWithObject(String url, T body, Object... pathVariables) {
        //noinspection unchecked
        return (T)template.postForObject(prefix + url, body, body.getClass(), pathVariables);
    }

}
