/**
 * @author XiongJie, Date: 14-7-30
 */

package dnt.itsnow.repository;

import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.service.Pageable;
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

    @Select("SELECT count(0) FROM itsnow_msc.contracts WHERE msp_account_id = #{mspId} or msp_account_id is null")
    long countByMspAccountId(@Param("mspId") long mspId);

    @Select("SELECT count(0) FROM itsnow_msc.contracts")
    long count();

    //不需要加载details，但需要分页
    List<Contract> findAll(@Param("pageable")Pageable pageable);

    List<Contract> findAllByMsuAccountId(@Param("msuId") long msuId, @Param("pageable")Pageable pageable);

    List<Contract> findAllByMspAccountId(@Param("mspId") long mspId, @Param("pageable")Pageable pageable);

    // 需要加载details
    Contract findBySn(String sn);
}
