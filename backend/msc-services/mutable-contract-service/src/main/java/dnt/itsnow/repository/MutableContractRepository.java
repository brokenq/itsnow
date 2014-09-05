package dnt.itsnow.repository;

import dnt.itsnow.model.Contract;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by jacky on 2014/9/4.
 */
public interface MutableContractRepository extends CommonContractRepository{

    /**
     * <h2>新增一个合同</h2>
     *
     * @param contract 新建的合同
     */
    @Insert("INSERT INTO itsnow_msc.contracts(sn,msu_account_id,msp_account_id, msu_status) " +
            "VALUES(#{sn}, #{msuAccountId}, #{mspAccountId}, #{msuStatus})")
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
            " sn = #{sn}"+
            " WHERE id = #{id}")
    void update(Contract contract);

    /**
     * <h2>批准合同</h2>
     *
     * 将某个合同的状态修改为 Valid
     *
     * @param contract 被修改的合同对象
     */
    @Update("UPDATE itsnow_msc.contracts SET msu_status = 'Valid' WHERE id = #{id}")
    void approve(Contract contract);

    /**
     * <h2>拒绝合同</h2>
     *
     * 将某个合同的状态修改为 Rejected
     *
     * @param contract 被修改的合同对象
     */
    @Update("UPDATE itsnow_msc.contracts SET msu_status = 'Rejected' WHERE id = #{id}")
    void reject(Contract contract);

    /**
     * 根据sn删除合同
     *
     * @param sn 合同的序号
     */
    @Delete("DELETE FROM itsnow_msc.contracts WHERE sn = #{sn}")
    void deleteBySn(@Param("sn") String sn);
}
