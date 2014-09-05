package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * Created by jacky on 2014/9/2.
 */
public interface PublicServiceItemRepository extends CommonServiceItemRepository{

    @Insert("INSERT INTO itsnow_msc.public_service_items (catalog_id,title,brief,description,icon) "+
            " values(#{catalog.id},#{title},#{brief},#{description},#{icon})")
    PublicServiceItem save(PublicServiceItem publicServiceItem);

    @Update("UPDATE itsnow_msc.public_service_items SET updated_at = CURRENT_TIMESTAMP," +
            "title = #{title},"+
            "brief = #{brief},"+
            "description = #{description},"+
            "icon = #{icon}"+
            " WHERE id = #{id}")
    PublicServiceItem update(PublicServiceItem publicServiceItem);

    @Delete("DELETE FROM itsnow_msc.public_service_items WHERE id = #{id}")
    void delete(@Param("id") Long id);

    @Insert("INSERT INTO itsnow_msc.account_service_items(account_id,item_id) " +
            "VALUES (#{accountId},#{itemId})")
    void addByAccount(@Param("itemId")Long itemId,@Param("accountId")Long accountId);

    @Delete("DELETE FROM itsnow_msc.account_service_items WHERE account_id = #{accountId} and item_id = #{itemId}")
    void deleteByAccount(@Param("itemId")Long itemId,@Param("accountId")Long accountId);
}
