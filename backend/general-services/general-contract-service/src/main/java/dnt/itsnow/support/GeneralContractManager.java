/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ContractException;
import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.platform.remote.service.RestFacade;
import dnt.itsnow.platform.service.ServiceException;
import dnt.itsnow.service.GeneralContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  <h1> The general contract manager</h1>
 *
 *  MSU/MSP的合同管理
 */
@Service
public class GeneralContractManager extends ContractManager implements GeneralContractService {
    @Autowired
    RestFacade facade;
    @Override
    public Contract approve(Account account, String sn) throws ServiceException {
        logger.info("Approving {} {}", account, sn);
        Contract contract = findByAccountAndSn(account, sn, true);
        if( account.isMsu() ){
            if( contract.isApprovedByMsu() ){
                throw new ContractException("The contract has been approved by msu");
            }
        }else if (account.isMsp() ){
            if( contract.isApprovedByMsp() ){
                throw new ContractException("The contract has been approved by msu");
            }
        }
        facade.put("/admin/api/contracts/{}/approve", contract.getSn());
        contract = repository.findBySn(sn);//update it after put
        logger.info("Approved  {} {}", account, sn);
        return contract;
    }

    @Override
    public Contract reject(Account account, String sn) throws ServiceException {
        logger.info("Rejecting {} {}", account, sn);
        Contract contract = findByAccountAndSn(account, sn, true);
        if( account.isMsu() ){
            if( !contract.isApprovedByMsu() ){
                throw new ServiceException("The contract has been rejected by msu");
            }
        }else if (account.isMsp() ){
            if( !contract.isApprovedByMsp() ){
                throw new ServiceException("The contract has been rejected by msu");
            }
        }
        facade.put("/admin/api/contracts/{}/reject", contract.getSn());
        contract = repository.findBySn(sn);//update it after put
        logger.info("Rejected  {} {}", account, sn);
        return contract;
    }

    @Override
    public ContractDetail updateDetail(ContractDetail detail) {
        logger.info("Updating {}", detail);
        ContractDetail contractDetail = facade.putWithObject("/admin/api/contracts/{}/details/{}",
                detail, detail.getContract().getSn(), detail.getId());
        logger.info("Updated  {}", detail);
        return contractDetail;
    }
}
