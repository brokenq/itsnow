package dnt.itsnow.repository;

import dnt.itsnow.model.PrivateServiceItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * <h1>Private Service Item Repository</h1>
 */
public interface PrivateServiceItemRepository extends CommonServiceItemRepository {

    List<PrivateServiceItem> findAllPrivate();

    PrivateServiceItem findPrivateById(Long id);

    @Insert("INSERT INTO private_service_items (public_id,catalog_id,title,brief,description,icon) "+
            " values(#{public_id},#{catalog.id},#{title},#{brief},#{description},#{icon})")
    PrivateServiceItem savePrivate(PrivateServiceItem privateServiceItem);

    @Delete("DELETE FROM private_service_items WHERE id = #{id}")
    void deletePrivate(Long id);
}
