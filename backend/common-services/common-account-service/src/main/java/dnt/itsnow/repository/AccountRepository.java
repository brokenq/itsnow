/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

/**
 * Account Repository
 */
public interface AccountRepository {
    @Select("SELECT * FROM itsnow_msc.accounts where name = #{name}")
    @ResultMap("accountResult")
    Account findByName(@Param("name") String name);

    @Select("SELECT * FROM itsnow_msc.accounts where sn = #{sn}")
    @ResultMap("accountResult")
    Account findBySn(@Param("sn") String sn);

    @Select("SELECT * FROM itsnow_msc.accounts where id = #{id}")
    @ResultMap("accountResult")
    Account findById(@Param("id") Long id);
}
