package dnt.itsnow.support;

import dnt.itsnow.model.ContractDetail;
import dnt.itsnow.repository.MutableContractDetailRepository;
import dnt.itsnow.service.MutableContractDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * <h1>ContractDetail实现</h1>
 */
@Service
public class MutableContractDetailManager implements MutableContractDetailService {

    @Autowired
    MutableContractDetailRepository mutableContractDetailRepository;

    /**
     * 创建一条合同详细条款
     * @param contractDetail 合同详细条款
     * @return 创建的合同详细条款
     */
    @Override
    public ContractDetail create(ContractDetail contractDetail) {
        contractDetail.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        contractDetail.setUpdatedAt(contractDetail.getCreatedAt());
        mutableContractDetailRepository.create(contractDetail);
        return contractDetail;
    }

    /**
     * 更新合同详细条款
     * @param contractDetail 合同详细条款
     * @return 更新后的合同详细条款
     */
    @Override
    public ContractDetail update(ContractDetail contractDetail) {
        contractDetail.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        mutableContractDetailRepository.update(contractDetail);
        return contractDetail;
    }

    /**
     * 删除一条合同详细条款
     * @param contractDetail
     */
    @Override
    public void delete(ContractDetail contractDetail) {
        mutableContractDetailRepository.deleteById(contractDetail.getId());
    }

}
