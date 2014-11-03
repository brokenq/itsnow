/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ContractException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.model.ContractStatus;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.service.GeneralContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

/**
 *  <h1> The general contract manager</h1>
 *
 *  MSU/MSP的合同管理
 */
@Service
public class GeneralContractManager extends CommonContractManager implements GeneralContractService {
    @Autowired
    @Qualifier("mscRestTemplate")
    RestOperations facade;

    @Override
    public Contract approve(Account account, String sn) throws ServiceException {
        logger.info("Approving {} {}", account, sn);
        Contract contract = findByAccountAndSn(account, sn, true);
        if (account.isMsu()) {
            if (contract.isApprovedByMsu()) {
                throw new ContractException("The contract has been approved by msu");
            }
        } else if (account.isMsp()) {
            if (contract.isApprovedByMsp()) {
                throw new ContractException("The contract has been approved by msu");
            }
        }
        facade.put("/admin/api/contracts/{sn}/approve", null, contract.getSn());
        contract = repository.findBySn(sn);//update it after put
        logger.info("Approved  {} {}", account, sn);
        return contract;
    }

    @Override
    public Contract reject(Account account, String sn) throws ServiceException {
        logger.info("Rejecting {} {}", account, sn);
        Contract contract = findByAccountAndSn(account, sn, true);
        if( account.isMsu() ){
            //if( !contract.isApprovedByMsu() ){
            //    throw new ServiceException("The contract has been rejected by msu");
            //}
        }else if (account.isMsp() ){
            if( !contract.isApprovedByMsp() ){
                throw new ServiceException("The contract has been rejected by msu");
            }
        }
        facade.put("/admin/api/contracts/{sn}/reject", null, contract.getSn());
        contract = repository.findBySn(sn);//update it after put
        logger.info("Rejected  {} {}", account, sn);
        return contract;
    }

    @Override
    public ContractDetail createDetail(ContractDetail detail, String sn) throws ServiceException {
        logger.info("Creating {}", detail);
        facade.postForEntity("/admin/api/contracts/{sn}/details", detail, ContractDetail.class, sn);
        logger.info("Created  {}", detail);
        return detail;
    }

    @Override
    public ContractDetail updateDetail(ContractDetail detail, String sn) {
        logger.info("Updating {}", detail);
        facade.put("/admin/api/contracts/{sn}/details/{}",
                detail, detail.getContract().getSn(), detail.getId());
        logger.info("Updated  {}", detail);
        return detail;
    }

    @Override
    public Contract bid(Account account, String sn) throws ServiceException{
        logger.info("Msp bid contract:{} {}", account, sn);
        Contract contract = findByAccountAndSn(account, sn, true);
        contract.setMspStatus(ContractStatus.Purposed);
        contract.setMspAccountId(account.getId());
        facade.put("/admin/api/contracts/{sn}/bid", contract, contract.getSn());
        logger.info("Msp bid contract {}", contract);
        return contract;
    }

    @Override
    public Contract create(Account account, Contract contract) throws ServiceException{
        contract.setMsuStatus(ContractStatus.Draft);
        contract.setMsuAccountId(account.getId());
        facade.postForEntity("/admin/api/contracts",contract,Contract.class);
        logger.info("Created  {}", contract);
        return contract;
    }


}
