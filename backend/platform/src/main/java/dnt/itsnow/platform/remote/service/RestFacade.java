/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.platform.remote.service;

/**
 * <h1>远端接口</h1>
 */
public interface RestFacade {
    void put(String url, Object... pathVariables);
    <T> T putWithObject(String url, T body, Object... pathVariables);
}
