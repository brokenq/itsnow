/**
 * @author XiongJie, Date: 14-7-25
 */
package dnt.itsnow.support;

import dnt.itsnow.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <h1>基于Mybatis Repository的持久化Token Repository</h1>
 * TODO 添加测试用例
 */
@Component
public class CustomizedTokenRepository implements PersistentTokenRepository {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        tokenRepository.save(token);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        tokenRepository.update(series, tokenValue, lastUsed);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return tokenRepository.findBySeries(seriesId);
    }

    @Override
    public void removeUserTokens(String username) {
        tokenRepository.delete(username);
    }
}
