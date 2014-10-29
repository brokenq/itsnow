package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceItem;
import org.apache.ibatis.annotations.*;

/**
 * Created by jacky on 2014/9/2.
 */
public interface PublicServiceItemRepository extends CommonServiceItemRepository{

    @Insert("INSERT INTO itsnow_msc.public_service_items (catalog_id,sn,title,brief,description,icon,created_at,updated_at) "+
            " values(#{catalog.id},#{sn},#{title},#{brief},#{description},#{icon},#{createdAt},#{updatedAt})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void save(PublicServiceItem publicServiceItem);

    @Update("UPDATE itsnow_msc.public_service_items SET updated_at = #{updatedAt}," +
            "title = #{title},"+
            "brief = #{brief},"+
            "description = #{description},"+
            "icon = #{icon}"+
            " WHERE id = #{id}")
    void update(PublicServiceItem publicServiceItem);

    @Delete("DELETE FROM itsnow_msc.public_service_items WHERE sn = #{sn}")
    void delete(@Param("sn") String sn);

    @Insert("INSERT INTO itsnow_msc.account_service_items(account_id,item_id) " +
            "VALUES (#{accountId},#{itemId})")
    void addByAccount(@Param("itemId")Long itemId,@Param("accountId")Long accountId);

    @Delete("DELETE FROM itsnow_msc.account_service_items WHERE account_id = #{accountId} and item_id = #{itemId}")
    void deleteByAccount(@Param("itemId")Long itemId,@Param("accountId")Long accountId);
}
