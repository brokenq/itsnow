/**
 * Developer: Kadvin Date: 14-7-10 下午9:07
 */
package dnt.itsnow.services.support;

import dnt.itsnow.services.exception.SessionException;
import dnt.itsnow.services.model.Session;
import dnt.itsnow.services.api.SessionService;
import dnt.spring.Bean;
import dnt.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The Session Service Implementations
 */
@Service
public class SessionManager extends Bean implements SessionService{
    //
    // TODO Session Store 应该被一个 SessionRepository 代替
    // Session Repository 可以有
    //   MemorySessionStore
    //   DatabaseSessionStore
    //   RedisSessionStore 等
    private Map<String, Session> sessionStore = new LinkedHashMap<String, Session>();

    @Override
    public Session challenge(String requestedSessionId, String username, String password) throws SessionException {
        if ("admin".equalsIgnoreCase(username)) {
            if (!"secret".equals(password)) {
                throw new SessionException("The password is invalid");
            }
            Session session = new Session();
            if(StringUtils.isBlank(requestedSessionId)){
                session.setSessionId(UUID.randomUUID().toString());
            }else{
                session.setSessionId(requestedSessionId);
            }
            session.setNickName("超级管理员");
            session.setUsername("Admin");
            sessionStore.put(session.getSessionId(), session);
            return session;
        } else{
            throw new SessionException("The username is invalid");
        }
    }

    @Override
    public String remember(Session session, int maxAge) {
        return UUID.randomUUID().toString();
    }

    @Override
    public Session find(String sessionId) {
        return sessionStore.get(sessionId);
    }

    @Override
    public void destroy(Session session) {
        sessionStore.remove(session.getSessionId());
    }

    @Override
    public void destroy(String requestSessionId) {
        sessionStore.remove(requestSessionId);
    }
}
