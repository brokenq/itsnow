/**
 * Developer: Kadvin Date: 14-7-10 下午9:07
 */
package dnt.itsnow.demo.support;

import dnt.itsnow.demo.api.UserService;
import dnt.itsnow.demo.exception.SessionException;
import dnt.itsnow.demo.model.Session;
import dnt.itsnow.demo.api.SessionService;
import dnt.itsnow.demo.model.User;
import dnt.itsnow.demo.repository.SessionRepository;
import net.happyonroad.spring.Bean;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The Session Service Implementations
 */
@Service
public class SessionManager extends Bean implements SessionService{
    @Autowired
    private UserService userService;
    //
    // TODO Session Store 应该被一个 SessionRepository 代替
    // Session Repository 可以有
    //   MemorySessionStore
    //   DatabaseSessionStore
    //   RedisSessionStore 等
    //private Map<String, Session> sessionStore = new LinkedHashMap<String, Session>();
    @Autowired
    SessionRepository repository;

    @Override
    public Session challenge(String requestedSessionId, String username, String password) throws SessionException {
        User user = userService.find(username, password);

        if ( user != null ) {
            Session session = new Session();
            if(StringUtils.isBlank(requestedSessionId)){
                session.setSessionId(UUID.randomUUID().toString());
            }else{
                session.setSessionId(requestedSessionId);
            }
            session.setNickName(user.getNickName());
            session.setUserName(user.getName());
            repository.save(session);
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
        return repository.findById(sessionId);
    }

    @Override
    public void destroy(Session session) {
        repository.destroy(session.getSessionId());
    }

    @Override
    public void destroy(String requestSessionId) {
        repository.destroy(requestSessionId);
    }
}
