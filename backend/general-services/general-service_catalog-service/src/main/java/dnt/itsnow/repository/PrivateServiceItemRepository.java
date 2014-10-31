package dnt.itsnow.repository;

import dnt.itsnow.model.PrivateServiceItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * <h1>Private Service Item Repository</h1>
 */
public interface PrivateServiceItemRepository extends CommonServiceItemRepository {

    List<PrivateServiceItem> findAllPrivate();

    PrivateServiceItem findPrivateBySn(String sn);

    @Insert("INSERT INTO private_service_items (public_id,catalog_id,sn,title,brief,description,icon,created_at,updated_at) "+
            " values(#{publicId},#{catalog.id},#{sn},#{title},#{brief},#{description},#{icon},#{createdAt},#{updatedAt})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void savePrivate(PrivateServiceItem privateServiceItem);

    @Delete("DELETE FROM private_service_items WHERE sn = #{sn}")
    void deletePrivate(String sn);
}
