package dnt.itsnow.service;

import dnt.itsnow.model.ContractDetail;

/**
 * <h1>Contract Detail Service</h1>
 */
public interface MutableContractDetailService{

    /**
     * 创建一条合同详细条款
     * @param contractDetail 合同详细条款
     * @return 创建的合同详细条款
     */
    ContractDetail create(ContractDetail contractDetail);

    /**
     * 更新合同详细条款
     * @param contractDetail 合同详细条款
     * @return 更新后的合同详细条款
     */
    ContractDetail update(ContractDetail contractDetail);

    /**
     * 删除一条合同详细条款
     * @param contractDetail
     */
    void delete(ContractDetail contractDetail);
}
