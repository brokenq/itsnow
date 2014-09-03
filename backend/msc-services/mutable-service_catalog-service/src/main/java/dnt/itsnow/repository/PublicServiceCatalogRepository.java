package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceCatalog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by jacky on 2014/9/2.
 */
public interface PublicServiceCatalogRepository {

    List<PublicServiceCatalog> findAll();

    @Insert("INSERT INTO public_service_catalogs (parent_id,sn,title,description,icon) "+
            " values(#{parentId},#{sn},#{title},#{description},#{icon})")
    PublicServiceCatalog save(PublicServiceCatalog publicServiceCatalog);

    @Update("UPDATE public_service_catalogs SET updated_at = CURRENT_TIMESTAMP," +
            "title = #{title},"+
            "description = #{description},"+
            "icon = #{icon}"+
            " WHERE sn = #{sn}")
    PublicServiceCatalog update(PublicServiceCatalog publicServiceCatalog);

    @Delete("DELETE FROM public_service_catalogs WHERE sn = #{sn}")
    void delete(String sn);
}
