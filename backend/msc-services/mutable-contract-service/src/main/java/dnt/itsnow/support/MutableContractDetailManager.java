package dnt.itsnow.support;

import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.repository.MutableContractDetailRepository;
import dnt.itsnow.service.MutableContractDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by jacky on 2014/9/4.
 */
@Service
public class MutableContractDetailManager implements MutableContractDetailService {

    @Autowired
    MutableContractDetailRepository mutableContractDetailRepository;

    @Override
    public ContractDetail create(ContractDetail contractDetail) {
        contractDetail.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        contractDetail.setUpdatedAt(contractDetail.getCreatedAt());
        mutableContractDetailRepository.create(contractDetail);
        return contractDetail;
    }

    @Override
    public ContractDetail update(ContractDetail contractDetail) {
        contractDetail.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        mutableContractDetailRepository.update(contractDetail);
        return contractDetail;
    }

    @Override
    public void delete(ContractDetail contractDetail) {
        mutableContractDetailRepository.deleteById(contractDetail.getId());
    }

}
