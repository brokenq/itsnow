package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceCatalog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

/**
 * Created by jacky on 2014/9/2.
 */
public interface PublicServiceCatalogRepository extends CommonServiceCatalogRepository {

    @Insert("INSERT INTO public_service_catalogs (parent_id,sn,title,description,icon,level,created_at,updated_at) "+
            " values(#{parentId},#{sn},#{title},#{description},#{icon},#{level},#{createdAt},#{updatedAt})")
    @Options(useGeneratedKeys = true,keyColumn = "id")
    void create(PublicServiceCatalog publicServiceCatalog);

    @Update("UPDATE public_service_catalogs SET updated_at = #{updatedAt}," +
            "title = #{title},"+
            "description = #{description},"+
            "icon = #{icon}"+
            " WHERE sn = #{sn}")
    void update(PublicServiceCatalog publicServiceCatalog);

    @Delete("DELETE FROM public_service_catalogs WHERE sn = #{sn}")
    void delete(String sn);
}
