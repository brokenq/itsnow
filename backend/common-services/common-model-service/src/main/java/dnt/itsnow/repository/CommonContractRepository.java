/**
 * @author XiongJie, Date: 14-7-30
 */

package dnt.itsnow.repository;

import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractMspAccount;
import dnt.itsnow.model.ContractMspUser;
import net.happyonroad.platform.service.Pageable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <h1>合同仓库服务，包括对合同明细的查询能力</h1>
 *
 * TODO 编写测试用例
 */
public interface CommonContractRepository {

    @Select("SELECT count(0) FROM itsnow_msc.contracts WHERE msu_account_id = #{msuId}")
    long countByMsuAccountId(@Param("msuId") long msuId);

    @Select("SELECT count(0) FROM itsnow_msc.contracts")
    long count();

    //不需要加载details，但需要分页
    List<Contract> findAll(@Param("pageable")Pageable pageable);

    List<Contract> findAllByMsuAccountId(@Param("msuId") long msuId, @Param("pageable")Pageable pageable);

    @Select("SELECT count(0) FROM itsnow_msc.contracts WHERE status IN ( 'Draft', 'Purposed' )")
    long countByMspDraft(@Param("mspId") long mspId);

    /**
     * MSP模块邀约管理
     * @param mspId MSP账户ID
     * @param pageable 分页
     * @return 合同列表
     */
    List<Contract> findAllByMspDraft(@Param("mspId") Long mspId, @Param("pageable")Pageable pageable);

    long countByMspAccountId(@Param("mspId") long mspId);

    /**
     * MSP模块我的合约
     * @param mspId MSP账户ID
     * @param pageable 分页
     * @return 合同列表
     */
    List<Contract> findAllByMspAccountId(@Param("mspId") Long mspId, @Param("pageable")Pageable pageable);

    // 需要加载details
    Contract findBySn(String sn);

    /**
     * 根据合同号查询批准到MSU登录的MSP用户列表
     * @param id 合同ID
     * @return MSP用户列表
     */
    List<ContractMspUser> findMspUserById(@Param("id") Long id);

    /**
     * 根据合同号加载应约的MSP账户及其相应的合同状态（批准或拒绝）
     * @param id 合同ID
     * @return 带合同状态的MSP账户
     */
    List<ContractMspAccount> findMspAccountById(@Param("id") Long id);
}
