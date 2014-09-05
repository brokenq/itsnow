package dnt.itsnow.support;

import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.repository.MutableContractDetailRepository;
import dnt.itsnow.service.MutableContractDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jacky on 2014/9/4.
 */
@Service
public class MutableContractDetailManager implements MutableContractDetailService {

    @Autowired
    MutableContractDetailRepository mutableContractDetailRepository;

    @Override
    public ContractDetail create(ContractDetail contractDetail) {
        mutableContractDetailRepository.create(contractDetail);
        return contractDetail;
    }

    @Override
    public ContractDetail update(ContractDetail contractDetail) {
        mutableContractDetailRepository.update(contractDetail);
        return contractDetail;
    }

    @Override
    public void delete(ContractDetail contractDetail) {
        mutableContractDetailRepository.deleteById(contractDetail.getId());
    }

}
