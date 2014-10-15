package dnt.itsnow.repository;

import dnt.itsnow.model.Workflow;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.PageRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>地点类持久层</h1>
 */
public interface WorkflowRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO workflows " +
            "(sn, name, description, act_re_procdef_id, service_item_id, service_item_type, process_dictionary_id, created_at, updated_at) " +
            "VALUES " +
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

    public int count(@Param("serviceTtemType") String serviceTtemType,
                     @Param("keyword") String keyword);

    public List<Workflow> find(
            @Param("serviceTtemType") String serviceTtemType,
            @Param("keyword") String keyword,
            @Param("pageable") Pageable pageable);

    public Workflow findBySn(@Param("sn") String sn, @Param("serviceTtemType") String serviceTtemType);

}
