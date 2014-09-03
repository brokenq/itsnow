package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 * Created by jacky on 2014/9/2.
 */
public interface PublicServiceItemRepository extends CommonServiceItemRepository{

    @Insert("INSERT INTO public_service_items (catalog_id,title,brief,description,icon) "+
            " values(#{catalog.id},#{title},#{brief},#{description},#{icon})")
    PublicServiceItem save(PublicServiceItem publicServiceItem);

    @Update("UPDATE public_service_items SET updated_at = CURRENT_TIMESTAMP," +
            "title = #{title},"+
            "brief = #{brief},"+
            "description = #{description},"+
            "icon = #{icon}"+
            " WHERE id = #{id}")
    PublicServiceItem update(PublicServiceItem publicServiceItem);

    @Delete("DELETE FROM public_service_items WHERE id = #{id}")
    void delete(Long id);
}
