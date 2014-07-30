/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.platform.web.model;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/** <h1>HTTP 路由项目</h1> */
public class RouteItem implements Comparable<RouteItem> {
    private final Map<RequestParam, String> params;
    private final String            httpMethod;
    private final String            url;
    private final String            handler;

    public RouteItem(String httpMethod, String url, String handler, Map<RequestParam, String> requestParams) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.handler = handler;
        this.params = requestParams;
    }

    @Override
    public String toString() {
        return String.format("%6s %-70s : %s", httpMethod, urlWithParams(), handler);
    }

    private String urlWithParams() {
        if( this.params == null || this.params.isEmpty()) return url;
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        for (Map.Entry<RequestParam,String> entry : params.entrySet()) {
            RequestParam param = entry.getKey();
            sb.append(param.value()).append("=")
              .append("{").append(entry.getValue())
              .append(param.required() ? "!" : "").append("}").append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    @Override
    public int compareTo(RouteItem other) {
        int urlResult = this.url.compareTo(other.url);
        if (urlResult != 0) return urlResult;
        return this.httpMethod.compareTo(other.httpMethod);
    }
}
