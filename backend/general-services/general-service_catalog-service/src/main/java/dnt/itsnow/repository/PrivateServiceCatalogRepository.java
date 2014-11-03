package dnt.itsnow.repository;

import dnt.itsnow.model.PrivateServiceCatalog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <h1>Private Service Catalog Repository</h1>
 */
public interface PrivateServiceCatalogRepository extends CommonServiceCatalogRepository {


    List<PrivateServiceCatalog> findAllPrivate();

    PrivateServiceCatalog findPrivateBySn(String sn);

    @Insert("INSERT INTO private_service_catalogs (public_id,parent_id,sn,title,description,icon,level,created_at,updated_at) "+
            " values(#{publicId},#{parentId},#{sn},#{title},#{description},#{icon},#{level},#{createdAt},#{updatedAt})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void savePrivate(PrivateServiceCatalog privateServiceCatalog);

    @Update("UPDATE private_service_catalogs SET updated_at = #{updatedAt}," +
            "title = #{title},"+
            "description = #{description},"+
            "icon = #{icon}"+
            " WHERE sn = #{sn}")
    void updatePrivate(PrivateServiceCatalog privateServiceCatalog);

    @Delete("DELETE FROM private_service_catalogs WHERE sn = #{sn}")
    void deletePrivate(String sn);

}
