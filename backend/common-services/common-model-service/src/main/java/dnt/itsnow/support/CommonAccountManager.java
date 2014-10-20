/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.support;

import dnt.itsnow.model.Account;
import dnt.itsnow.repository.CommonAccountRepository;
import dnt.itsnow.service.CommonAccountService;
import dnt.spring.Bean;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h1>基本的账户服务</h1>
 * 提供给当前用户，仅提供其关联的企业的帐户信息只读功能
 */
@Service
public class CommonAccountManager extends Bean implements CommonAccountService {

    @Autowired
    CommonAccountRepository repository;

    @Override
    public Account findByName(@Param("name") String name) {
        logger.trace("Finding account by name {}", name);
        Account account = repository.findByName(name);
        logger.trace("Found   account {}", account);
        return account;
    }

    @Override
    public Account findByDomain(String domain) {
        logger.trace("Finding account by domain {}", domain);
        Account account = repository.findByDomain(domain);
        logger.trace("Found   account {}", account);
        return account;
    }

    @Override
    public Account findBySn(String sn) {
        logger.trace("Finding account by sn {}", sn);
        Account account = repository.findBySn(sn);
        logger.trace("Found   account {}", account);
        return account;
    }

    @Override
    public Account findById(Long id) {
        logger.debug("Finding account by id {}", id);
        Account account = repository.findById(id);
        logger.trace("Found   account {}", account);
        return account;
    }
}
