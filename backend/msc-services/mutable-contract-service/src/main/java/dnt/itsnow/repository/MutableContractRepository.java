package dnt.itsnow.repository;

import dnt.itsnow.model.Contract;
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
    @Insert("INSERT INTO itsnow_msc.contracts(sn, msu_account_id, msp_account_id, status, created_at, updated_at) " +
            "VALUES(#{sn}, #{msuAccountId}, #{mspAccountId}, #{status}, #{createdAt}, #{updatedAt})")
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
            "SET msu_account_id = #{msuAccountId}," +
            " msp_account_id = #{mspAccountId}," +
            " status = #{status},"+
            " sn = #{sn},"+
            " updated_at = #{updatedAt}"+
            " WHERE id = #{id}")
    void update(Contract contract);

    /**
     * <h2>MSP投标</h2>
     *
     * 将合同的msp状态修改为 Proposed
     *
     * @param contract 被修改的合同对象
     */
    @Update("UPDATE itsnow_msc.contracts" +
            " SET status = #{status}," +
            " msp_account_id = #{mspAccountId},"+
            " updated_at = #{updatedAt}"+
            " WHERE id = #{id}")
    void bid(Contract contract);

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
     * 查询是否有特定的MSP用户已经可以登录特定的MSU系统
     * @param accountId MSU账户
     * @param userId MSP用户
     * @return int
     */
    @Select("SELECT count(*) FROM contract_users WHERE msu_account_id = #{accountId} AND msp_user_id = #{userId}")
    int findRelation(@Param("userId")Long userId, @Param("accountId")Long accountId);

    /**
     * 建立MSP用户可以登录MSU系统的关系
     * @param accountId MSU账户
     * @param userId MSP用户
     */
    @Insert("INSERT INTO contract_users(msp_user_id, msu_account_id) VALUES (#{userId}, #{accountId})")
    void buildRelation(@Param("userId")Long userId, @Param("accountId")Long accountId);

    /**
     * 建立MSP用户可以登录MSU系统的关系
     * @param accountId MSU账户
     */
    @Delete("DELETE FROM contract_users WHERE msu_account_id = #{accountId}")
    void deleteRelation(@Param("accountId")Long accountId);
}
