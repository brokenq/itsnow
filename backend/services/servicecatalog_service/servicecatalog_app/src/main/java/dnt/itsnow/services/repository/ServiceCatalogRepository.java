/**
 * Developer: Kadvin Date: 14-7-4 上午9:26
 */
package dnt.itsnow.services.repository;

import dnt.itsnow.services.model.ServiceCatalog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>映射用户会话</h1>
 *
 * 默认的做法（无需自己指定SQL）:
 * <pre>
 *     selectSession(int):Session;
 *     selectSessions():List&lt;Session&gt;
 *       or
 *     <code>@MapKey("session_id")</code>
 *     selectSessions():Map&lt;String,Session&gt;
 *     insertSession(Session):int
 *     updateSession(Session):int
 *     deleteSession(int):int
 * </pre>
 */
public interface ServiceCatalogRepository {
    @Delete("delete from service_catalog where id = #{id}")
    void delete(@Param("id") Integer id);

    @Update("update service_catalog set sc_name=#{scName},sc_desc=#{scDesc},updated_at=CURRENT_TIMESTAMP where id = #{id}")
    void update(ServiceCatalog serviceCatalog);

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("insert into service_catalog(id, sc_name,sc_desc, created_at, updated_at) " +
            "values(#{id}, #{sc_name},#{sc_desc}, #{createdAt}, #{updatedAt})")
    void save(ServiceCatalog serviceCatalog);

    @Select("select * from service_catalog where sc_name = #{name}")
    ServiceCatalog findByName(@Param("name") String name);

    @Select("select * from service_catalog where id = #{id}")
    ServiceCatalog findById(@Param("id") Integer id);

    @Select("select count(0) from service_catalog")
    int count();//如果是count，mybatis会说已经这么映射了，但我貌似又没法用到

    @Select("select count(0) from service_catalog where sc_name like '%#{keyword}%' or sc_desc like '%#{keyword}%'")
    int countByKeyword(@Param("keyword") String keyword);

    @Select("select * from service_catalog " +
            " order by #{orderBy} limit #{offset}, #{size}")
    List<ServiceCatalog> findServiceCatalogs(@Param("orderBy") String orderBy,
                         @Param("offset") int offset,
                         @Param("size") int size);

    @Select("select * from service_catalog where sc_name like '%#{keyword}%' or sc_desc like '%#{keyword}%'" +
            " order by #{orderBy} limit #{offset}, #{size}")
    List<ServiceCatalog> findServiceCatalogsByKeyword(@Param("keyword") String keyword,
                                  @Param("orderBy") String orderBy,
                                  @Param("offset") int offset,
                                  @Param("size") int size);


}
