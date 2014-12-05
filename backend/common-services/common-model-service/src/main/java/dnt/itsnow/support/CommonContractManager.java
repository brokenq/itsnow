/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.exception.ContractException;
import dnt.itsnow.model.*;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.service.Pageable;
import net.happyonroad.platform.service.ServiceException;
import net.happyonroad.platform.util.DefaultPage;
import dnt.itsnow.repository.CommonContractRepository;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.CommonContractService;
import net.happyonroad.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1> The common contract manager</h1>
 * <p/>
 * 通用的合同管理
 * <p/>
 * TODO 添加测试用例
 */
@Service
@Transactional
public class CommonContractManager extends Bean implements CommonContractService {

    @Autowired
    CommonContractRepository repository;
    @Autowired
    CommonAccountService accountService;

    @Override
    public Page<Contract> findAllByAccount(Account account, Boolean own, Pageable pageable) throws ServiceException {

        Assert.notNull(account, "the account shouldn't be null");

        long total;
        List<Contract> contracts;

        if (account.isMsc()) {
            total = repository.count();
            contracts = repository.findAll(pageable);
        } else if (account.isMsu()) {
            total = repository.countByMsuAccountId(account.getId());
            contracts = repository.findAllByMsuAccountId(account.getId(), pageable);
        } else if (account.isMsp()) {
            if (own) {
                total = repository.countByMspAccountId(account.getId());
                contracts = repository.findAllByMspAccountId(account.getId(), pageable);
            } else {
                total = repository.countByMspDraft(account.getId());
                contracts = repository.findAllByMspDraft(account.getId(), pageable);
            }
        } else {
            throw new AccountException("The account type is invalid!");
        }

        for (Contract contract : contracts) {
            this.formatContract(contract);
        }

        if(account.isMsp() && own){
            for(Contract con : contracts){
                for (ContractMspAccount mspAccount : con.getMspAccounts()){
                    if(account.getSn().equals(mspAccount.getSn())){
                        // 对于此MSP用户，看这个合同的状态应该是批准或拒绝，而不是合同真正的状态
                        con.setStatus(mspAccount.getContractStatus());
                        // 相应的，这个是对于MSP用户来说，这个合同的应约时间，而不是这个合同真正的创建时间
                        con.setCreatedAt(mspAccount.getCreatedAt());
                    }
                }
            }
        }

        return new DefaultPage<Contract>(contracts, pageable, total);
    }

    @Override
    public Contract findByAccountAndSn(Account account, String sn, boolean withDetails) throws ServiceException {
        Assert.notNull(account, "the account shouldn't be null");
        Contract contract = repository.findBySn(sn);
        if (account.isMsu() && !contract.getMsuAccountId().equals(account.getId())) {
            throw new ContractException(account + " should be MSU of the contract: " + contract);
        }
        this.formatContract(contract);
        return contract;
    }

    @Override
    public Contract findBySn(String sn) throws ServiceException {
        Contract contract = repository.findBySn(sn);
        List<ContractMspUser> mspUsers = repository.findMspUserById(contract.getId());
        contract.setMspUsers(mspUsers);
        return contract;
    }

    private void formatContract(Contract contract) {
        if (contract == null)
            return;

        // MSU Account
        Long id = contract.getMsuAccountId();
        Account account = accountService.findById(id);
        contract.setMsuAccount((dnt.itsnow.model.MsuAccount) account);

        // MSP Accounts
        List<ContractMspAccount> mspAccounts = repository.findMspAccountById(contract.getId());
        contract.setMspAccounts(mspAccounts);
    }
}
