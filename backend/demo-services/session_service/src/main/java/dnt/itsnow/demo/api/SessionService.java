/**
 * Developer: Kadvin Date: 14-7-10 下午9:06
 */
package dnt.itsnow.demo.api;

import dnt.itsnow.demo.exception.SessionException;
import dnt.itsnow.demo.model.Session;

/**
 * The Session Service
 */
public interface SessionService {
    /**
     * 判断用户名密码是否正确
     *
     *
     * @param requestedSessionId HTTP请求会话ID，可能为空
     * @param username 用户名
     * @param password 密码
     * @return 对应会话对象
     */
    Session challenge(String requestedSessionId, String username, String password) throws SessionException;

    /**
     * 记录相应的会话
     *
     * @param session 会话
     * @param maxAge  记录的时间
     * @return 记录的标记
     */
    String remember(Session session, int maxAge);

    /**
     * 根据会话id查找相应的会话对象
     *
     * @param sessionId 会话id
     * @return 会话对象
     */
    Session find(String sessionId);

    /**
     * 注销会话
     *
     * @param session 被注销的会话
     */
    void destroy(Session session);

    /**
     * 根据HTTP SESSION ID注销
     * @param requestSessionId HTTP的会话标记
     */
    void destroy(String requestSessionId);
}
