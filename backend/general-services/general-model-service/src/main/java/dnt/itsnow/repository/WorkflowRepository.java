package dnt.itsnow.repository;

import dnt.itsnow.model.Workflow;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>地点类持久层</h1>
 */
public interface WorkflowRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO workflows (sn, name, description, act_re_procdef_id, service_item_id, service_item_type, process_dictionary_id, created_at, updated_at) VALUES " +
            "(#{sn}, #{name}, #{description}, #{actReProcdef.id_}, #{serviceItem.id}, #{serviceTtemType}, #{processDictionary.id}, #{createdAt}, #{updatedAt})")
    public void create(Workflow workflow);

    @Delete("DELETE FROM workflows WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE workflows SET " +
            " sn                    = #{sn}, " +
            " name                  = #{name}, " +
            " description           = #{description}," +
            " act_re_procdef_id     = #{actReProcdef.id_}," +
            " service_item_id      = #{serviceItem.id}," +
            " service_item_type    = #{serviceTtemType}," +
            " process_dictionary_id = #{processDictionary.id}, " +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(Workflow workflow);

    @Select("select count(0) from workflows")
    public int count();

    //    @Select("select * from workflows order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<Workflow> find(
            @Param("serviceTtemType") String serviceTtemType,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    @Select("select count(*) from workflows where name like #{keyword} or sn like #{keyword}")
    public int countByKeyword(@Param("keyword") String keyword);

    //    @Select("select * from workflows where name like #{keyword} or sn like #{keyword}" +
//            " order by ${sort} ${dir} limit #{offset}, #{size}")
    public List<Workflow> findByKeyword(
            @Param("serviceTtemType") String serviceTtemType,
            @Param("keyword") String keyword,
            @Param("sort") String sort,
            @Param("dir") String dir,
            @Param("offset") int offset,
            @Param("size") int size);

    public Workflow findBySn(@Param("sn") String sn, @Param("serviceTtemType") String serviceTtemType);
}
