package dnt.itsnow.repository;

import dnt.itsnow.model.Site;
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

    @Select("select count(0) from sites")
    public int count();

    public List<Site> findAll(
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from sites where name like #{keyword} or sn like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    public List<Site> findAllByKeyword(@Param("keyword") String keyword,
                                    @Param("sort") String sort,
                                    @Param("dir") String dir,
                                    @Param("offset") int offset,
                                    @Param("size") int size);

    public Site findBySn(@Param("sn") String sn);
}
