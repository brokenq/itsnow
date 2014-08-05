/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.support;

import dnt.itsnow.model.Account;
import dnt.itsnow.repository.AccountRepository;
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
    AccountRepository repository;

    @Override
    public Account findByName(@Param("name") String name) {
        logger.debug("Finding account by name {}", name);
        return repository.findByName(name);
    }

    @Override
    public Account findBySn(String sn) {
        logger.debug("Finding account by sn {}", sn);
        return repository.findBySn(sn);
    }

    @Override
    public Account findById(Long id) {
        logger.debug("Finding account by id {}", id);
        return repository.findById(id);
    }
}
