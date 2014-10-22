/**
 * @author XiongJie, Date: 14-7-30
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.Account;
import org.apache.ibatis.annotations.Param;

/**
 * Account Repository
 */
public interface CommonAccountRepository {
    Account findByName(@Param("name") String name);

    Account findByDomain(@Param("domain") String domain);


    Account findBySn(@Param("sn") String sn);

    Account findById(@Param("id") Long id);
}
