/**
 * @author XiongJie, Date: 14-7-30
 */

package dnt.itsnow.repository;

import dnt.itsnow.model.Account;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 针对账户的数据库操作
 */
public interface MutableAccountRepository extends AccountRepository{
    long countByType(@Param("type") String type);

    List<Account> findAllByType(@Param("type") String type, @Param("pageable") Pageable pageable);

    @Insert("INSERT INTO itsnow_msc.accounts(sn, name, type, status) " +
            "VALUES(#{sn}, #{name}, #{type} #{status}")
    long create(Account account);

    @Insert("UPDATE itsnow_msc.accounts SET name = #{name}, status = #{status} WHERE id = #{id}")
    void update(Account account);

    @Delete("DELETE FROM itsnow_msc.accounts WHERE sn = #{sn}")
    void deleteBySn(@Param("sn") String sn);

    @Select("SELECT * FROM itsnow_msc.accounts WHERE  id = #{id}")
    Account findById(@Param("id") long id);
}
