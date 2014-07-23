/**
 * xiongjie on 14-7-22.
 */
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AccountService {
    @Select("SELECT * FROM itsnow_msc.accounts where name = #{name}")
    Account findByName(@Param("name") String name);
}
