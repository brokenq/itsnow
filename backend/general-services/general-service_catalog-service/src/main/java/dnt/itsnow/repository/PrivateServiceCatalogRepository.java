package dnt.itsnow.repository;

import dnt.itsnow.model.PrivateServiceCatalog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 * <h1>Private Service Catalog Repository</h1>
 */
public interface PrivateServiceCatalogRepository extends CommonServiceCatalogRepository {


    List<PrivateServiceCatalog> findAllPrivate();

    PrivateServiceCatalog findPrivateBySn(String sn);

    @Insert("INSERT INTO private_service_catalogs (public_id,parent_id,sn,title,description,icon) "+
            " values(#{publicId},#{parentId},#{sn},#{title},#{description},#{icon})")
    PrivateServiceCatalog savePrivate(PrivateServiceCatalog privateServiceCatalog);

    @Delete("DELETE FROM private_service_catalogs WHERE sn = #{sn}")
    void deletePrivate(String sn);

}
