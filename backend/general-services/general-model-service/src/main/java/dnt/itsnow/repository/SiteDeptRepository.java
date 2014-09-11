package dnt.itsnow.repository;

import dnt.itsnow.model.Department;
import dnt.itsnow.model.SiteDept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <h1>地点类持久层</h1>
 */
public interface SiteDeptRepository {

    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO site_depts (site_id, dept_id) VALUES " +
            "(#{site.id}, #{department.id})")
    public void create(SiteDept siteDept);

    public void createByBatch(SiteDept siteDept);

    /**
     * 根据地址ID删除地点与部门关系表
     * @param siteId 地点ID
     */
    @Delete("DELETE FROM site_depts WHERE site_id = #{siteId}")
    public void deleteSiteAndDeptRelationBySiteId (@Param("siteId") long siteId);

    /**
     * 根据部门ID删除部门与地点关系表
     * @param deptId 部门ID
     */
    @Delete("DELETE FROM site_depts WHERE dept_id = #{deptId}")
    public void deleteDeptAndSiteRelationByDeptId(@Param("deptId") long deptId);

    @Update("UPDATE site_depts SET " +
            " site_id  = #{site.id}, " +
            " dept_id  = #{department.id} " +
            " WHERE id = #{id} ")
    public void update(SiteDept siteDept);

    public SiteDept findById(@Param("id") long id);
}
