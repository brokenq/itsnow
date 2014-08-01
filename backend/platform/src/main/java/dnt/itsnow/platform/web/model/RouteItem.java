/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.platform.web.model;

import java.util.Map;

/** <h1>HTTP 路由项目</h1> */
public class RouteItem implements Comparable<RouteItem> {
    private final Map<String, String> params;
    private final String                    httpMethod;
    private final String                    url;
    private final String                    handler;
    private boolean showDetail = false;

    public RouteItem(String httpMethod, String url, String handler, Map<String, String> requestParams) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.handler = handler;
        this.params = requestParams;
    }

    @Override
    public String toString() {
        return showDetail ?
               String.format("%6s %-70s : %s", httpMethod, urlWithParams(), handler) :
               String.format("%6s %-100s", httpMethod, urlWithParams());
    }

    private String urlWithParams() {
        if (this.params == null || this.params.isEmpty()) return url;
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String param = entry.getKey();
            sb.append(param).append("=")
              .append("{").append(entry.getValue())
              .append("}").append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public int compareTo(@SuppressWarnings("NullableProblems") RouteItem other) {
        int urlResult = this.url.compareTo(other.url);
        if (urlResult != 0) return urlResult;
        return this.httpMethod.compareTo(other.httpMethod);
    }

    public void showDetail(boolean detail) {
        this.showDetail = detail;
    }
}
