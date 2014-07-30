/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Account Repository
 */
public interface AccountRepository {
    @Select("SELECT * FROM itsnow_msc.accounts where name = #{name}")
    Account findByName(@Param("name") String name);

    @Select("SELECT * FROM itsnow_msc.accounts where sn = #{sn}")
    Account findBySn(@Param("sn") String sn);
}
