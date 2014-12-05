package dnt.itsnow.repository;

import dnt.itsnow.model.Workflow;
import net.happyonroad.platform.service.Pageable;
import net.happyonroad.platform.util.PageRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>流程管理持久层</h1>
 */
public interface WorkflowRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO workflows " +
            "(sn, name, description, act_re_procdef_id, service_item_id, service_item_sn, service_item_type, type, created_at, updated_at) " +
            "VALUES " +
            "(#{sn}, #{name}, #{description}, #{actReProcdef.id}, #{serviceItem.id}, #{serviceItem.sn}, #{serviceItemType}, #{type}, #{createdAt}, #{updatedAt})")
    public void create(Workflow workflow);

    @Delete("DELETE FROM workflows WHERE sn = #{sn}")
    public void delete(@Param("sn") String sn);

    @Update("UPDATE workflows SET " +
            " sn                    = #{sn}, " +
            " name                  = #{name}, " +
            " description           = #{description}," +
            " act_re_procdef_id     = #{actReProcdef.id}," +
            " service_item_id       = #{serviceItem.id}," +
            " service_item_sn       = #{serviceItem.sn}," +
            " service_item_type     = #{serviceItemType}," +
            " type                  = #{type}, " +
            " created_at            = #{createdAt}, " +
            " updated_at            = #{updatedAt} " +
            " WHERE id              = #{id} ")
    public void update(Workflow workflow);

    public int count(@Param("keyword") String keyword);

    public List<Workflow> findAll(
            @Param("keyword") String keyword,
            @Param("pageable") Pageable pageable);

    public Workflow findBySn(@Param("sn") String sn);

    @Select("SELECT * FROM workflows WHERE name = #{name}")
    Workflow findByName(@Param("name") String name);
}
