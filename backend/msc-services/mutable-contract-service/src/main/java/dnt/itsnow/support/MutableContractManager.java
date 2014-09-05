package dnt.itsnow.support;

import dnt.itsnow.model.Contract;
import dnt.itsnow.repository.MutableContractRepository;
import dnt.itsnow.service.MutableContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jacky on 2014/9/4.
 */
@Service
public class MutableContractManager extends CommonContractManager implements MutableContractService{

    @Autowired
    MutableContractRepository mutableContractRepository;

    @Override
    public Contract create(Contract contract) {
        mutableContractRepository.create(contract);
        return contract;
    }

    @Override
    public Contract update(Contract contract) {
        mutableContractRepository.update(contract);
        return contract;
    }

    @Override
    public void delete(Contract contract) {
        //delete contract
        mutableContractRepository.deleteBySn(contract.getSn());
    }

}
