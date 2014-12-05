/**
 * Developer: Kadvin Date: 14-9-19 下午12:50
 */
package dnt.itsnow.support;

import dnt.itsnow.util.Configuration;
import dnt.itsnow.util.ItsnowCsrfToken;
import net.happyonroad.spring.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

/**
 * <h1> The MSC rest template</h1>
 */
@SuppressWarnings("UnusedDeclaration")
@Service("mscRestTemplate")
public class MscRestTemplate extends Bean implements RestOperations {
    private RestTemplate template = new RestTemplate();


    protected Configuration configuration;

    private boolean posted = false;

    public MscRestTemplate() {
        this.configuration = new Configuration();
        this.configuration
                .host(System.getProperty("msc.host", "localhost"))
                .port(Integer.valueOf(System.getProperty("msc.port", "8401")))
                .username("admin")
                .password("secret");
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /* url组装 */
    ////////////////////////////////////////////////////////////////////////////////////////////////

    protected String assemble(final String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) return url;
        return "http://" + configuration.getHost() + ":" + configuration.getPort()
                + (url.startsWith("/") ? url : "/" + url);
    }

    protected URI assemble(final URI url) {
        String str = url.toString();
        if( str.startsWith("http://") || str.startsWith("https://")) return url;
        try {
            return new URI(assemble(url.toString()));
        } catch (URISyntaxException e) {
            System.err.println("Can't add host/port prefix for: " + url);
            return url;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /* 高级封装 */
    ////////////////////////////////////////////////////////////////////////////////////////////////

    void withCsrf(final Job job) {
      withCsrf(new Callback<Object>() {
          @Override
          public Object perform(HttpHeaders headers) {
              job.perform(headers);
              return null;
          }
      });
    }

    <T> T withCsrf(Callback<T> callback) {
        if (configuration.getCsrf() == null) {
            HttpHeaders headers = configuration.requestHeaders();
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity<ItsnowCsrfToken> response = getForEntity("/security/csrf", ItsnowCsrfToken.class, entity);
            configuration.csrf(response.getBody());
            // 登录等前后会由服务器端发起改变cookie，所以，是否有cookie，不由客户端决定，而是由服务器端决定
            String newCookies = response.getHeaders().getFirst("Set-Cookie");
            if (newCookies != null ) {
                configuration.sessionCookies(newCookies);
            }
        }
        try {
            return callback.perform(configuration.requestHeaders());
        } finally {
            if (posted) {
                configuration.csrf(null);
            }
            this.posted = false;
        }
    }

    void withLoginUser(final Job job){
        withLoginUser(new Callback<Object>() {
            @Override
            public Object perform(HttpHeaders headers) {
                job.perform(headers);
                return null;
            }
        });
    }

    <T> T withLoginUser(Callback<T> callback){
        if( !configuration.isLogined() ){
            withCsrf(new Callback<URI>() {
                public URI perform(HttpHeaders headers) {
                    final HttpEntity request = new HttpEntity(headers);

                    try {
                        HttpEntity newRequest = newRequest(request, headers);
                        ResponseEntity<String> response = template.postForEntity(assemble("/api/session?username={username}&password={password}"),
                                request, String.class, configuration.getUsername(), configuration.getPassword());
                        String newCookies = response.getHeaders().getFirst("Set-Cookie");
                        if (newCookies != null ) {
                            configuration.sessionCookies(newCookies);
                        }
                        return response.getHeaders().getLocation();
                    } finally {
                        posted();
                    }


                    //ResponseEntity<String> response = postForEntity("/api/session?username={username}&password={password}",
                    //        request, String.class, configuration.getUsername(), configuration.getPassword());
                    //登录成功之后，改变session

                }
            });
            configuration.logined();
        }
        return withCsrf(callback);
    }


    @Override
    public <T> T getForObject(final String url, final Class<T> responseType, final Object... uriVariables) throws RestClientException {
        return withLoginUser(new Callback<T>() {
            @Override
            public T perform(HttpHeaders headers) {
                try {
                    HttpEntity<?> request = new HttpEntity<Object>(headers);
                    ResponseEntity<T> response = template.exchange(assemble(url), HttpMethod.GET, request, responseType, uriVariables);
                    return response.getBody();
                } finally {
                    normal();
                }
            }
        });
    }

    @Override
    public <T> T getForObject(final String url, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return withLoginUser(new Callback<T>() {
            @Override
            public T perform(HttpHeaders headers) {
                try {
                    HttpEntity<?> request = new HttpEntity<Object>(headers);
                    ResponseEntity<T> response = template.exchange(assemble(url), HttpMethod.GET, request, responseType, uriVariables);
                    return response.getBody();
                } finally {
                    normal();
                }
            }
        });
    }

    @Override
    public <T> T getForObject(final URI url, final Class<T> responseType) throws RestClientException {
        return withLoginUser(new Callback<T>() {
            @Override
            public T perform(HttpHeaders headers) {
                try {
                    HttpEntity<?> request = new HttpEntity<Object>(headers);
                    ResponseEntity<T> response = template.exchange(assemble(url), HttpMethod.GET, request, responseType);
                    return response.getBody();
                } finally {
                    normal();
                }
            }
        });
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(final String url, final Class<T> responseType, final Object... uriVariables) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity<?> request = new HttpEntity<Object>(headers);
                    return template.exchange(assemble(url), HttpMethod.GET, request, responseType, uriVariables);
                } finally {
                    normal();
                }
            }
        });
    }

    public <T> ResponseEntity<T> getForEntity(final String url, final Class<T> responseType, final HttpEntity request, final Object... uriVariables) throws RestClientException {
        //return withLoginUser(new Callback<ResponseEntity<T>>() {
            //@Override
            //public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    //HttpEntity<?> request = new HttpEntity<Object>(headers);
                    return template.exchange(assemble(url), HttpMethod.GET, request, responseType, uriVariables);
                } finally {
                    normal();
                }
            //}
        //});
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(final String url, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity<?> request = new HttpEntity<Object>(headers);
                    return template.exchange(assemble(url), HttpMethod.GET, request, responseType, uriVariables);
                } finally {
                    normal();
                }
            }
        });
    }


    @Override
    public <T> ResponseEntity<T> getForEntity(final URI url, final Class<T> responseType) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity<?> request = new HttpEntity<Object>(headers);
                    return template.exchange(assemble(url), HttpMethod.GET, request, responseType);
                } finally {
                    normal();
                }
            }
        });
    }

    @Override
    public HttpHeaders headForHeaders(final String url, final Object... uriVariables) throws RestClientException {
        try {
            return template.headForHeaders(assemble(url), uriVariables);
        } finally {
            normal();
        }
    }

    @Override
    public HttpHeaders headForHeaders(final String url, final Map<String, ?> uriVariables) throws RestClientException {
        try {
            return template.headForHeaders(assemble(url), uriVariables);
        } finally {
            normal();
        }
    }

    @Override
    public HttpHeaders headForHeaders(final URI url) throws RestClientException {
        try {
            return template.headForHeaders(assemble(url));
        } finally {
            normal();
        }
    }

    @Override
    public URI postForLocation(final String url, final Object request, final Object... uriVariables) throws RestClientException {
        return withLoginUser(new Callback<URI>() {
            @Override
            public URI perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    return template.postForLocation(assemble(url), newRequest, uriVariables);
                } finally {
                    posted();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    protected HttpEntity newRequest(Object request, HttpHeaders headers) {
        HttpEntity newRequest;
        if( request instanceof HttpEntity){
            newRequest = new HttpEntity(((HttpEntity) request).getBody(), headers);
        }else{
            newRequest = new HttpEntity(request, headers);
        }
        return newRequest;
    }

    @Override
    public URI postForLocation(final String url, final Object request, final Map<String, ?> uriVariables) throws RestClientException {
        return withLoginUser(new Callback<URI>() {
            @Override
            public URI perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    return template.postForLocation(assemble(url), newRequest, uriVariables);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public URI postForLocation(final URI url, final Object request) throws RestClientException {
        return withLoginUser(new Callback<URI>() {
            @Override
            public URI perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    return template.postForLocation(assemble(url), newRequest);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public <T> T postForObject(final String url, final Object request, final Class<T> responseType, final Object... uriVariables) throws RestClientException {
        return withLoginUser(new Callback<T>() {
            @Override
            public T perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    return template.postForObject(assemble(url), request, responseType, uriVariables);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public <T> T postForObject(final String url, final Object request, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return withLoginUser(new Callback<T>() {
            @Override
            public T perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    return template.postForObject(assemble(url), request, responseType, uriVariables);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public <T> T postForObject(final URI url, final Object request, final Class<T> responseType) throws RestClientException {
        return withLoginUser(new Callback<T>() {
            @Override
            public T perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    return template.postForObject(assemble(url), request, responseType);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(final String url, final Object request, final Class<T> responseType, final Object... uriVariables) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    return template.postForEntity(assemble(url), newRequest, responseType, uriVariables);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(final String url, final Object request, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    return template.postForEntity(assemble(url), request, responseType, uriVariables);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(final URI url, final Object request, final Class<T> responseType) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    return template.postForEntity(assemble(url), request, responseType);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public void put(final String url, final Object request, final Object... uriVariables) throws RestClientException {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    template.put(assemble(url), newRequest, uriVariables);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public void put(final String url, final Object request, final Map<String, ?> uriVariables) throws RestClientException {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    template.put(assemble(url), newRequest, uriVariables);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public void put(final URI url, final Object request) throws RestClientException {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(request, headers);
                    template.put(assemble(url), newRequest);
                } finally {
                    posted();
                }
            }
        });
    }

    @Override
    public void delete(final String url, final Object... uriVariables) throws RestClientException {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                try {
                    HttpEntity request = new HttpEntity(headers);
                    exchange(assemble(url), HttpMethod.DELETE, request, Object.class, uriVariables);
                } finally {
                    posted();
                }
            }
        });
        
    }

    @Override
    public void delete(final String url, final Map<String, ?> uriVariables) throws RestClientException {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                try {
                    HttpEntity request = new HttpEntity(headers);
                    exchange(assemble(url), HttpMethod.DELETE, request, Object.class, uriVariables);
                } finally {
                    posted();
                }
            }
        });
        
    }

    @Override
    public void delete(final URI url) throws RestClientException {
        withLoginUser(new Job() {
            @Override
            public void perform(HttpHeaders headers) {
                try {
                    HttpEntity request = new HttpEntity(headers);
                    exchange(assemble(url), HttpMethod.DELETE, request, Object.class);
                } finally {
                    posted();
                }
            }
        });
        
    }

    @Override
    public Set<HttpMethod> optionsForAllow(final String url, final Object... uriVariables) throws RestClientException {
        return template.optionsForAllow(assemble(url), uriVariables);
    }

    @Override
    public Set<HttpMethod> optionsForAllow(final String url, final Map<String, ?> uriVariables) throws RestClientException {
        return template.optionsForAllow(assemble(url), uriVariables);
    }

    @Override
    public Set<HttpMethod> optionsForAllow(final URI url) throws RestClientException {
        return template.optionsForAllow(assemble(url));
    }

    @Override
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity, final Class<T> responseType, final Object... uriVariables) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(requestEntity, headers);
                    return template.exchange(assemble(url), method, newRequest, responseType, uriVariables);
                } finally {
                    executed(method);
                }
            }
        });
    }

    @Override
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity, final Class<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(requestEntity, headers);
                    return template.exchange(assemble(url), method, newRequest, responseType, uriVariables);
                } finally {
                    executed(method);
                }
            }
        });
    }

    @Override
    public <T> ResponseEntity<T> exchange(final URI url, final HttpMethod method, final HttpEntity<?> requestEntity, final Class<T> responseType) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(requestEntity, headers);
                    return template.exchange(assemble(url), method, newRequest, responseType);
                } finally {
                    executed(method);
                }
            }
        });
    }

    @Override
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType, final Object... uriVariables) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(requestEntity, headers);
                    return template.exchange(assemble(url), method, requestEntity, responseType, uriVariables);
                } finally {
                    executed(method);
                }
            }
        });
    }

    @Override
    public <T> ResponseEntity<T> exchange(final String url, final HttpMethod method, final HttpEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType, final Map<String, ?> uriVariables) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(requestEntity, headers);
                    return template.exchange(assemble(url), method, requestEntity, responseType, uriVariables);
                } finally {
                    executed(method);
                }
            }
        });
    }

    @Override
    public <T> ResponseEntity<T> exchange(final URI url, final HttpMethod method, final HttpEntity<?> requestEntity, final ParameterizedTypeReference<T> responseType) throws RestClientException {
        return withLoginUser(new Callback<ResponseEntity<T>>() {
            @Override
            public ResponseEntity<T> perform(HttpHeaders headers) {
                try {
                    HttpEntity newRequest = newRequest(requestEntity, headers);
                    return template.exchange(assemble(url), method, requestEntity, responseType);
                } finally {
                    executed(method);
                }
            }
        });
    }

    @Override
    public <T> T execute(final String url, final HttpMethod method, final RequestCallback requestCallback, final ResponseExtractor<T> responseExtractor, final Object... uriVariables) throws RestClientException {
        try {
            return template.execute(assemble(url), method, requestCallback, responseExtractor, uriVariables);
        } finally {
            executed(method);
        }
    }

    @Override
    public <T> T execute(final String url, final HttpMethod method, final RequestCallback requestCallback, final ResponseExtractor<T> responseExtractor, final Map<String, ?> uriVariables) throws RestClientException {
        try {
            return template.execute(assemble(url), method, requestCallback, responseExtractor, uriVariables);
        } finally {
            executed(method);
        }
    }

    @Override
    public <T> T execute(final URI url, final HttpMethod method, final RequestCallback requestCallback, final ResponseExtractor<T> responseExtractor) throws RestClientException {
        try {
            return template.execute(assemble(url), method, requestCallback, responseExtractor);
        } finally {
            executed(method);
        }
    }

    ////////////////////////////////////////////////////////
    // 私有辅助方法
    ////////////////////////////////////////////////////////

    private void posted() {
        this.posted = true;
    }

    private void normal() {
        this.posted = false;
    }

    private void executed(HttpMethod method) {
        switch (method) {
            case POST:
            case PUT:
            case PATCH:
            case DELETE:
                this.posted();
                break;
            default: 
                this.normal();
        }
    }

    public static interface Callback<T> {
        T perform(HttpHeaders headers);
    }

    public static interface Job {
        void perform(HttpHeaders headers);
    }
}