package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * <h1>CommonServiceItem Repository</h1>
 */
public interface CommonServiceItemRepository {

    List<PublicServiceItem> findAll();

    PublicServiceItem findBySn(String sn);

    List<PublicServiceItem> findByAccountId(Long accountId);

    @Select("SELECT count(0) from itsnow_msc.account_service_items WHERE account_id = #{accountId} and item_id = #{itemId}")
    Long countByAccountAndItem(@Param("accountId")Long accountId,@Param("itemId")Long itemId);

    Set<Long> findIdsByCatalogIds(@Param("ids")Set<Long> ids);
}
