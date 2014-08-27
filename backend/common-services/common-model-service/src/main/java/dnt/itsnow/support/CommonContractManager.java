/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.exception.ContractException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.repository.CommonContractRepository;
import dnt.itsnow.service.CommonContractService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 *  <h1> The common contract manager</h1>
 *
 *  通用的合同管理
 *
 *  TODO 添加测试用例
 */
@Service
public class CommonContractManager extends Bean implements CommonContractService {
    @Autowired
    CommonContractRepository repository;

    @Override
    public Page<Contract> findAllByAccount(Account account, Pageable pageable) throws ServiceException{
        Assert.notNull(account, "the account shouldn't be null");
        if(account.isMsc())
            throw new AccountException("Don't support find contract of Carrier");
        if( account.isMsu()){
            long total = repository.countByMsuAccountId(account.getId());
            List<Contract> contracts = repository.findAllByMsuAccountId(account.getId(), pageable);
            return new DefaultPage<Contract>(contracts, pageable, total);
        }else if(account.isMsp()){
            long total = repository.countByMspAccountId(account.getId());
            List<Contract> contracts = repository.findAllByMspAccountId(account.getId(), pageable);
            return new DefaultPage<Contract>(contracts, pageable, total);
        }else{
            throw new AccountException("The account type is invalid!");
        }
    }

    @Override
    public Contract findByAccountAndSn(Account account, String sn, boolean withDetails) throws ServiceException{
        Assert.notNull(account, "the account shouldn't be null");
        if(account.isMsc())
            throw new AccountException("Don't support find contract of Carrier");
        else{
            Contract contract = repository.findBySn(sn);
            if( account.isMsu() && !contract.getMsuAccount().equals(account)){
                throw new ContractException(account + " should be MSU of the contract: " + contract);
            }
            if(account.isMsp() && !contract.getMspAccount().equals(account)){
                throw new ContractException(account + " should be MSP of the contract: " + contract);
            }
            return contract;
        }
    }

}
