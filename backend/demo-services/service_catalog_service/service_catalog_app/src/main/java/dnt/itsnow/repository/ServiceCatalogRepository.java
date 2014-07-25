package dnt.itsnow.repository;

import dnt.itsnow.services.model.ServiceCatalog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>映射Service Catalog</h1>
 */
public interface ServiceCatalogRepository {
    @Delete("delete from demo_service_catalogs where id = #{id}")
    void delete(@Param("id") Integer id);

    @Update("update demo_service_catalogs set sc_name=#{scName},sc_desc=#{scDesc},updated_at=CURRENT_TIMESTAMP where id = #{id}")
    void update(ServiceCatalog serviceCatalog);

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("insert into demo_service_catalogs(id, sc_name,sc_desc, created_at, updated_at) " +
            "values(#{id}, #{sc_name},#{sc_desc}, #{createdAt}, #{updatedAt})")
    void save(ServiceCatalog serviceCatalog);

    @Select("select * from demo_service_catalogs where sc_name = #{name}")
    ServiceCatalog findByName(@Param("name") String name);

    @Select("select * from demo_service_catalogs where id = #{id}")
    ServiceCatalog findById(@Param("id") Integer id);

    @Select("select count(0) from demo_service_catalogs")
    int count();//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到

    @Select("select count(0) from demo_service_catalogs where sc_name like '%#{keyword}%' or sc_desc like '%#{keyword}%'")
    int countByKeyword(@Param("keyword") String keyword);

    @Select("select * from demo_service_catalogs " +
            " order by #{orderBy} limit #{offset}, #{size}")
    List<ServiceCatalog> findServiceCatalogs(@Param("orderBy") String orderBy,
                         @Param("offset") int offset,
                         @Param("size") int size);

    @Select("select * from demo_service_catalogs where sc_name like '%#{keyword}%' or sc_desc like '%#{keyword}%'" +
            " order by #{orderBy} limit #{offset}, #{size}")
    List<ServiceCatalog> findServiceCatalogsByKeyword(@Param("keyword") String keyword,
                                  @Param("orderBy") String orderBy,
                                  @Param("offset") int offset,
                                  @Param("size") int size);


}
