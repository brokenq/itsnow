package dnt.itsnow.repository;

import dnt.itsnow.model.Site;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>地点类持久层</h1>
 */
public interface SiteRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO sites (sn, name, address, description, work_time_id, process_dictionary_id, created_at, updated_at) VALUES " +
            "(#{sn}, #{name}, #{address}, #{description}, #{workTime.id}, #{processDictionary.id} ,#{createdAt}, #{updatedAt})")
    public void create(Site site);

    @Delete("DELETE FROM sites WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE sites SET " +
            " sn                    = #{sn}, " +
            " name                  = #{name}, " +
            " address               = #{address}," +
            " description           = #{description}," +
            " work_time_id          = #{workTime.id}," +
            " process_dictionary_id = #{processDictionary.id}, " +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(Site site);

    public int count(@Param("keyword") String keyword);

    public List<Site> findAll(
            @Param("keyword") String keyword,
            @Param("pageable") Pageable pageable);

    public Site findBySn(@Param("sn") String sn);

}
