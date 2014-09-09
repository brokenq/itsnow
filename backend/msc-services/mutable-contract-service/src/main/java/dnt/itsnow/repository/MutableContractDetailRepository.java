package dnt.itsnow.repository;

import dnt.itsnow.model.ContractDetail;
import org.apache.ibatis.annotations.*;

/**
 * Created by jacky on 2014/9/4.
 */
public interface MutableContractDetailRepository {

    /**
     * <h2>新增一个合同Detail</h2>
     *
     * @param contractDetail 新建的合同Detail
     */
    @Insert("INSERT INTO itsnow_msc.contract_details(contract_id,title,brief,description,icon,item_id,created_at,updated_at) " +
            "VALUES(#{contract.id}, #{title}, #{brief}, #{description},#{icon},#{itemId},#{createdAt},#{updatedAt})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void create(ContractDetail contractDetail);

    /**
     * <h2>更新一个合同Detail</h2>
     *
     * 如果声明这个函数的返回值，其返回值为结果集中更新的记录数量
     *
     * @param contractDetail 更新的合同detail
     */
    @Update("UPDATE itsnow_msc.contract_details " +
            "SET title = #{title}," +
            " brief = #{brief}," +
            " description = #{description},"+
            " icon = #{icon},"+
            " item_id = #{itemId},"+
            " updated_at = #{updatedAt}"+
            " WHERE id = #{id}")
    void update(ContractDetail contractDetail);


    /**
     * 根据ID删除合同Detail
     *
     * @param id 合同Detail的序号
     */
    @Delete("DELETE FROM itsnow_msc.contract_details WHERE id = #{id}")
    void deleteById(@Param("id") Long id);
}
