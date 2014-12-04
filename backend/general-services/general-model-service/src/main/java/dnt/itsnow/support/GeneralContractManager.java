/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ContractException;
import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.service.CommonUserService;
import dnt.itsnow.service.GeneralContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1> The general contract manager</h1>
 * <p/>
 * MSU/MSP的合同管理
 */
@Service
public class GeneralContractManager extends CommonContractManager implements GeneralContractService {

    @Autowired
    @Qualifier("mscRestTemplate")
    RestOperations facade;

    @Autowired
    @Qualifier("plainUserService")
    CommonUserService commonUserService;

    @Override
    public Contract approve(Account account, String sn) throws ServiceException {

        logger.info("Approving {}, contract sn {}", account, sn);

        Contract contract = findByAccountAndSn(account, sn, true);

        logger.debug("Found {}", contract);

        if (account.isMsu()) {
            /*if (contract.getMspAccountId() == null) {
                throw new ContractException("The contract has not yet been accept by MSP");
            } else*/ if (contract.isApproved()) {
                throw new ContractException("The contract has been approved by msu");
            }
        } else if (account.isMsp()) {
            if (contract.isApproved()) {
                throw new ContractException("The contract has been approved by msp");
            }
        }
        facade.put("/admin/api/contracts/{sn}/approve", null, contract.getSn());
        contract = findByAccountAndSn(account, sn, true);

        logger.info("Approved  {} {}", account, sn);

        List<User> users = commonUserService.findUsersByAccount(account);
        contract.setUsers(users);
        buildRelation(contract);

        return contract;
    }

    @Override
    public Contract reject(Account account, String sn) throws ServiceException {
        logger.info("Rejecting {} {}", account, sn);
        Contract contract = findByAccountAndSn(account, sn, true);

        logger.debug("Found {}", contract);

        if (account.isMsu()) {
            /*if (contract.getMspAccountId() == null) {
                throw new ContractException("The contract has not yet been accept by MSP");
            }*/
            //if( !contract.isApprovedByMsu() ){
            //    throw new ServiceException("The contract has been rejected by msu");
            //}
        } else if (account.isMsp()) {
            if (!contract.isApproved()) {
                throw new ServiceException("The contract has been rejected by msu");
            }
        }
        facade.put("/admin/api/contracts/{sn}/reject", null, contract.getSn());
        contract = findByAccountAndSn(account, sn, true);
        logger.info("Rejected  {} {}", account, sn);
        return contract;
    }

    @Override
    public Contract bid(Account account, String sn) throws ServiceException {

        logger.info("Msp bid contract:{} {}", account, sn);

        if (!account.isMsp()) {
            throw new ServiceException("The contract bid ,only MSP users are allowed to do this.");
        }

        Contract contract = findByAccountAndSn(account, sn, true);

        /*if (ContractStatus.Purposed.equals(contract.getStatus())) {
            throw new ServiceException("The contract has been purposed");
        }*/

        contract.setStatus(ContractStatus.Purposed);
        facade.put("/admin/api/contracts/{mspAccountId}/bid", contract, account.getId());

        logger.info("Msp bid contract {}", contract);

        return contract;
    }

    @Override
    public Contract create(Account account, Contract contract) throws ServiceException {
        contract.setStatus(ContractStatus.Draft);
        contract.setMsuAccountId(account.getId());
        facade.postForEntity("/admin/api/contracts", contract, Contract.class);
        logger.info("Created  {}", contract);
        return contract;
    }

    @Override
    public ContractDetail createDetail(ContractDetail detail, String contractSn) throws ServiceException {
        logger.info("Creating {}", detail);
        facade.postForEntity("/admin/api/contracts/{sn}/details", detail, ContractDetail.class, contractSn);
        logger.info("Created  {}", detail);
        return detail;
    }

    @Override
    public ContractDetail updateDetail(ContractDetail detail, String sn) throws ServiceException {
        logger.info("Updating {}", detail);
        facade.put("/admin/api/contracts/{sn}/details/{id}",
                detail, detail.getContract().getSn(), detail.getId());
        logger.info("Updated  {}", detail);
        return detail;
    }

    @Override
    public Contract buildRelation(Contract contract) throws ServiceException {
        logger.info("Building {}", contract.getUsers());
        facade.postForEntity("/admin/api/contracts/user/relation",
                contract, Contract.class);
        logger.info("Built    {}", contract.getUsers());
        return contract;
    }

    @Override
    public Contract updateRelation(Contract contract) throws ServiceException {
        logger.info("Updating {}", contract.getUsers());
        facade.put("/admin/api/contracts/user/relation", contract);
        logger.info("Updated  {}", contract.getUsers());
        return contract;
    }

}
