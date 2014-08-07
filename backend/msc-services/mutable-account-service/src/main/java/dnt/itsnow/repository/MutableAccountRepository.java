/**
 * @author XiongJie, Date: 14-7-30
 */

package dnt.itsnow.repository;

import dnt.itsnow.model.Account;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 针对账户的数据库操作
 *
 * <p>
 * TODO Mutable Repository 是不是应该继承 Common Repository 这是一个值得探讨的问题
 *
 * 初步决定，应该继承，理由如下：
 * <ol>
 * <li>Common  Account Manager autowire Common Account Repository
 * <li>Mutable Account Manager autowire Mutable Account Repository
 * <li>Mutable Account Manager inherit Common Account Manager
 * <li>Mutable Account Manager autowire Common Account Repository
 * </ol>
 * 如果 MutableAccountRepository 不从 CommonAccountRepository 继承，
 * 则为了让 Mutable Account Manager正确工作，需要将Common Account Repository 也跨组件暴露
 * 这违反了我们组件开发的原则：组件之间应该暴露服务，而不是服务的底层支撑
 * 备注：平台倒是可以暴露服务的底层支撑
 * </p>
 */
public interface MutableAccountRepository extends CommonAccountRepository {
    long countByType(@Param("type") String type);

    List<Account> findAllByType(@Param("type") String type, @Param("pageable") Pageable pageable);

    /**
     * <h2>新增一个账户</h2>
     *
     * 如果声明这个函数的返回值，其返回值为结果集中新增的数量，并不是新纪录的id
     *
     * @param account 新建的账户
     */
    @Insert("INSERT INTO itsnow_msc.accounts(sn, name, type, status) " +
            "VALUES(#{sn}, #{name}, #{type}, #{status})")
    void create(Account account);

    /**
     * <h2>更新一个账户</h2>
     *
     * 如果声明这个函数的返回值，其返回值为结果集中更新的记录数量
     *
     * @param account 新建的账户
     */
    @Update("UPDATE itsnow_msc.accounts SET name = #{name}, status = #{status} WHERE id = #{id}")
    void update(Account account);

    @Delete("DELETE FROM itsnow_msc.accounts WHERE sn = #{sn}")
    void deleteBySn(@Param("sn") String sn);
}
