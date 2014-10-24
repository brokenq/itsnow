package dnt.itsnow.service;

import dnt.itsnow.exception.DepartmentException;
import dnt.itsnow.model.Department;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * <h1>部门管理业务层</h1>
 */
public interface DepartmentService {

    /**
     * <h2>创建部门信息</h2>
     * @param department 新增的部门
     * @return 信息
     * @throws DepartmentException
     */
    public Department create(Department department) throws DepartmentException;

    /**
     * <h2>销毁部门信息</h2>
     * @param department 待销毁部门
     * @throws DepartmentException
     */
    public void destroy(Department department) throws DepartmentException;

    /**
     * <h2>修改部门信息</h2>
     * @param department 待修改部门
     * @return 已完成修改部门信息
     * @throws DepartmentException
     */
    public Department update(Department department) throws DepartmentException;

    /**
     * <h2>根据序列号查询部门信息</h2>
     * @param sn 部门序列号
     * @return 部门信息
     */
    public Department findBySn(String sn);

    /**
     * <h2>查询所有的部门</h2>
     * @param keyword 查询关键字
     * @param isTree 是否生成树形结构标记，true为生成
     * @return 部门集合
     */
    public List<Department> findAll(String keyword, boolean isTree);

    /**
     * <h2>查询所有包含有此ID的子部门</h2>
     * @param id 当前部门的ID
     * @return 当前部门下的子部门
     */
    public List<Department> findAllByParentId(long id);

}
