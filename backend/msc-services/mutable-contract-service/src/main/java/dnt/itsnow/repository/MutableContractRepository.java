package dnt.itsnow.repository;

import dnt.itsnow.model.Contract;
import dnt.itsnow.model.ContractUser;
import dnt.itsnow.model.User;
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
    @Insert("INSERT INTO itsnow_msc.contracts(sn,msu_account_id,msp_account_id, msu_status,created_at,updated_at) " +
            "VALUES(#{sn}, #{msuAccountId}, #{mspAccountId}, #{msuStatus},#{createdAt},#{updatedAt})")
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
            " msu_status = #{msuStatus},"+
            " msp_status = #{mspStatus},"+
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
            " SET msp_status = #{mspStatus}," +
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
            " SET msu_status = #{msuStatus}, " +
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
            " SET msu_status = #{msuStatus}," +
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
     * 建立合同与MSP用户的关联关系
     * @param contractUser 合同与MSP用户关系实体类
     */
    void buildRelation(ContractUser contractUser);

    /**
     * 修改MSP用户根据合同的关联关系，访问MSU系统的权限
     * @param contractUser 合同与MSP用户关系实体类
     */
    void updateRelation(ContractUser contractUser);
}
