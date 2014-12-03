package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceCatalog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * <h1>CommonServiceCatalog Repository</h1>
 */
public interface CommonServiceCatalogRepository {

    List<PublicServiceCatalog> findAll();

    PublicServiceCatalog findBySn(String sn);

    List<PublicServiceCatalog> findByAccountId(Long accountId);

    @Select("SELECT count(0) from itsnow_msc.account_service_catalogs WHERE account_id = #{accountId} and catalog_id = #{catalogId}")
    Long countByAccountAndCatalog(@Param("accountId")Long accountId,@Param("catalogId")Long catalogId);

    Set<Long> findIdsByParentIds(@Param("ids")Set<Long> ids);
}
