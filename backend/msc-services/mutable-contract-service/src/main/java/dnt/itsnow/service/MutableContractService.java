package dnt.itsnow.service;

import dnt.itsnow.model.Contract;

/**
 * <h1>Contract Service</h1>
 */
public interface MutableContractService extends CommonContractService{

    /**
     * 创建合同，目前只允许MSU创建
     * @param contract 合同信息
     * @return 创建后的合同信息
     */
    Contract create(Contract contract);

    /**
     * 更新合同信息
     * @param contract 合同信息
     * @return 更新后的合同信息
     */
    Contract update(Contract contract);

    /**
     * MSP投标，更新合同状态
     * @param contract 合同信息
     * @return 更新后的合同信息
     */
    Contract bid(Contract contract);

    /**
     * MSU确认合同
     * @param contract 合同信息
     * @return 更新后的合同信息
     */
    Contract approve(Contract contract);

    /**
     * MSU拒绝合同
     * @param contract 合同信息
     * @return 更新后的合同信息
     */
    Contract reject(Contract contract);

    /**
     * 删除合同
     * @param contract 合同信息
     */
    void delete(Contract contract);
}
