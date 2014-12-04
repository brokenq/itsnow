package dnt.itsnow.support;

import dnt.itsnow.exception.AccountException;
import dnt.itsnow.exception.ContractException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.model.MspAccount;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.CommonContractRepository;
import dnt.itsnow.service.CommonAccountService;
import dnt.itsnow.service.CommonContractService;
import dnt.spring.Bean;
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
        if (account.isMsc()) {
            long total = repository.count();
            List<Contract> contracts = repository.findAll(pageable);
            for (Contract contract : contracts) {
                this.formatContract(contract);
            }
            return new DefaultPage<Contract>(contracts, pageable, total);
        }
        if (account.isMsu()) {
            long total = repository.countByMsuAccountId(account.getId());
            List<Contract> contracts = repository.findAllByMsuAccountId(account.getId(), pageable);
            for (Contract contract : contracts) {
                this.formatContract(contract);
            }
            return new DefaultPage<Contract>(contracts, pageable, total);
        } else if (account.isMsp()) {
            long total = repository.countByMspAccountId(account.getId());
            List<Contract> contracts;
            if (own) {
                contracts = repository.findAllByMspAccountId(account.getId(), pageable);
            } else {
                contracts = repository.findAllByMspDraft(account.getId(), pageable);
            }
            for (Contract contract : contracts) {
                this.formatContract(contract);
            }
            return new DefaultPage<Contract>(contracts, pageable, total);
        } else {
            throw new AccountException("The account type is invalid!");
        }
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
        return repository.findBySn(sn);
    }

    private void formatContract(Contract contract) {
        if (contract == null)
            return;

        // MSU Account
        Long id = contract.getMsuAccountId();
        Account account = accountService.findById(id);
        contract.setMsuAccount((dnt.itsnow.model.MsuAccount) account);

        // MSP Accounts
        List<Long> mspAccountIds = repository.findMspAccountById(contract.getId());
        List<MspAccount> mspAccounts = new ArrayList<MspAccount>();
        for(Long mspAccountId : mspAccountIds){
            Account mspAccount = accountService.findById(mspAccountId);
            mspAccounts.add((MspAccount)mspAccount);
        }
        contract.setMspAccounts(mspAccounts);
    }
}
