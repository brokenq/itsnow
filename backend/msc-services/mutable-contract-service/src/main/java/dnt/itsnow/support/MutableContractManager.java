package dnt.itsnow.support;

import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractMspAccount;
import dnt.itsnow.model.ContractStatus;
import dnt.itsnow.model.User;
import dnt.itsnow.repository.MutableContractRepository;
import dnt.itsnow.service.MutableContractService;
import net.happyonroad.platform.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MutableContractManager extends CommonContractManager implements MutableContractService {

    @Autowired
    MutableContractRepository mutableContractRepository;

    /**
     * 创建合同，目前只允许MSU创建
     *
     * @param contract 合同信息
     * @return 创建后的合同信息
     */
    @Override
    public Contract create(Contract contract) {
        contract.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        contract.setUpdatedAt(contract.getCreatedAt());
        mutableContractRepository.create(contract);
        return contract;
    }

    /**
     * 更新合同信息
     *
     * @param contract 合同信息
     * @return 更新后的合同信息
     */
    @Override
    public Contract update(Contract contract) {
        contract.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        mutableContractRepository.update(contract);
        return contract;
    }

    /**
     * MSP投标，更新合同状态
     *
     * @param contract 合同信息
     * @return 更新后的合同信息
     */
    @Override
    public Contract bid(String mspAccountId, Contract contract) {
        contract.setStatus(ContractStatus.Purposed);
        contract.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        mutableContractRepository.bid(contract);
        mutableContractRepository.bidRecord(mspAccountId, contract.getId());
        return contract;
    }

    /**
     * MSU确认合同
     *
     * @param contract 合同信息
     * @return 更新后的合同信息
     */
    @Override
    public Contract approve(Contract contract) {
        contract.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        contract.setStatus(ContractStatus.Approved);
        mutableContractRepository.approve(contract);

        for(ContractMspAccount account : contract.getMspAccounts()){
            mutableContractRepository.updateRecord(contract.getId(), account.getId(), account.getContractStatus());
        }

        return contract;
    }

    /**
     * MSU拒绝合同
     *
     * @param contract 合同信息
     * @return 更新后的合同信息
     */
    @Override
    public Contract reject(Contract contract) {
        contract.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        contract.setStatus(ContractStatus.Rejected);
        mutableContractRepository.reject(contract);
        return contract;
    }

    /**
     * 删除合同
     *
     * @param contract 合同信息
     */
    @Override
    public void delete(Contract contract) {
        //delete contract
        mutableContractRepository.deleteBySn(contract.getSn());
    }

    @Override
    public void buildRelation(Contract contract) {
        for (User user : contract.getMspUsers()) {
                mutableContractRepository.buildRelation(contract.getId(), user.getId(), "1");
        }
    }

    @Override
    public void updateRelation(Contract contract) {
        mutableContractRepository.deleteRelation(contract.getId());
        buildRelation(contract);
    }

}
