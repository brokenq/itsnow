package dnt.itsnow.support;

import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractStatus;
import dnt.itsnow.repository.MutableContractRepository;
import dnt.itsnow.service.MutableContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by jacky on 2014/9/4.
 */
@Service
public class MutableContractManager extends CommonContractManager implements MutableContractService{

    @Autowired
    MutableContractRepository mutableContractRepository;

    @Override
    public Contract create(Contract contract) {
        contract.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        contract.setUpdatedAt(contract.getCreatedAt());
        mutableContractRepository.create(contract);
        return contract;
    }

    @Override
    public Contract update(Contract contract) {
        contract.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        mutableContractRepository.update(contract);
        return contract;
    }

    @Override
    public Contract bid(Contract contract) {
        contract.setMspStatus(ContractStatus.Purposed);
        contract.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        mutableContractRepository.bid(contract);
        return contract;
    }

    @Override
    public Contract approve(Contract contract) {
        contract.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        contract.setMsuStatus(ContractStatus.Approved);
        mutableContractRepository.approve(contract);
        return contract;
    }

    @Override
    public Contract reject(Contract contract) {
        contract.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        contract.setMsuStatus(ContractStatus.Rejected);
        mutableContractRepository.reject(contract);
        return contract;
    }

    @Override
    public void delete(Contract contract) {
        //delete contract
        mutableContractRepository.deleteBySn(contract.getSn());
    }

}
