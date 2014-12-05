package dnt.itsnow.repository;

import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractStatus;
import org.apache.ibatis.annotations.*;

/**
 * <h1>Contract Repository</h1>
 */
public interface MutableContractRepository extends CommonContractRepository{

    /**
     * <h2>新增一个合同</h2>
     *
     * @param contract 新建的合同
     */
    @Insert("INSERT INTO itsnow_msc.contracts(msu_account_id, sn, title, type, status, created_at, updated_at) " +
            "VALUES(#{msuAccountId}, #{sn}, #{title}, #{type}, #{status}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void create(Contract contract);

    /**
     * <h2>更新一个合同</h2>
     *
     * 如果声明这个函数的返回值，其返回值为结果集中更新的记录数量
     *
     * @param contract 更新的合同
     */
    @Update("UPDATE itsnow_msc.contracts " +
            "SET title = #{title}," +
            " type = #{type},"+
            " status = #{status},"+
            " updated_at = #{updatedAt}"+
            " WHERE id = #{id}")
    void update(Contract contract);

    /**
     * <h2>MSP应约</h2>
     *
     * 将合同的msp状态修改为 Proposed
     *
     * @param contract 被修改的合同对象
     */
    @Update("UPDATE itsnow_msc.contracts" +
            " SET status = #{status}," +
            " updated_at = #{updatedAt}"+
            " WHERE id = #{id}")
    void bid(Contract contract);

    /**
     * 记录哪些MSP应约了合同
     * @param mspAccountId MSP账户
     * @param id 合同ID
     */
    @Insert("INSERT INTO itsnow_msc.contract_records(contract_id, msp_account_id) " +
            "VALUES(#{id}, #{mspAccountId})")
    void bidRecord(@Param("mspAccountId")String mspAccountId, @Param("id")Long id);

    /**
     * 修改MSP应约记录
     * @param id 合同ID
     * @param mspAccountId 应约的MSP账户ID
     * @param status MSU对应约账户的回复状态
     */
    @Update("UPDATE itsnow_msc.contract_records SET status = #{status} WHERE contract_id = #{id} AND msp_account_id = #{mspAccountId}")
    void updateRecord(@Param("id")Long id, @Param("mspAccountId")Long mspAccountId, @Param("status")ContractStatus status);

    /**
     * <h2>批准合同</h2>
     *
     * 将某个合同的状态修改为 Valid
     *
     * @param contract 被修改的合同对象
     */
    @Update("UPDATE itsnow_msc.contracts" +
            " SET status = #{status}, " +
            " updated_at = #{updatedAt}"+
            " WHERE id = #{id}")
    void approve(Contract contract);

    /**
     * <h2>拒绝合同</h2>
     *
     * 将某个合同的状态修改为 Rejected
     *
     * @param contract 被修改的合同对象
     */
    @Update("UPDATE itsnow_msc.contracts" +
            " SET status = #{status}," +
            " updated_at = #{updatedAt}"+
            " WHERE id = #{id}")
    void reject(Contract contract);

    /**
     * 根据sn删除合同
     *
     * @param sn 合同的序号
     */
    @Delete("DELETE FROM itsnow_msc.contracts WHERE sn = #{sn}")
    void deleteBySn(@Param("sn") String sn);

    /**
     * 建立MSP用户可以登录MSU系统的关系
     * @param contractId 合同ID
     * @param userId MSP用户
     * @param access MSU用户是否允许MSP用户登录标记
     */
    @Insert("INSERT INTO itsnow_msc.contract_users(contract_id, msp_user_id, access) VALUES (#{contractId}, #{userId}, #{access})")
    void buildRelation(@Param("contractId")Long contractId, @Param("userId")Long userId, @Param("access")String access);

    /**
     * 建立MSP用户可以登录MSU系统的关系
     * @param contractId 合同ID
     */
    @Delete("DELETE FROM itsnow_msc.contract_users WHERE contract_id = #{contractId}")
    void deleteRelation(@Param("contractId")Long contractId);
}
