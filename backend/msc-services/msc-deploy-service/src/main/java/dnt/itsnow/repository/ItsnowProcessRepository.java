/**
 * Developer: Kadvin Date: 14-9-15 上午10:44
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.platform.util.PageRequest;

import java.util.List;

/**
 * <h1>Itsnow Process Repository</h1>
 */
public interface ItsnowProcessRepository {
    int countByKeyword(String keyword);

    List<ItsnowProcess> findAllByKeyword(String keyword, PageRequest request);

    //TODO FIND 出来应该带上 host, schema, account等关联对象
    ItsnowProcess findByName(String name);

    void create(ItsnowProcess creating);

    void deleteByName(String name);
}
