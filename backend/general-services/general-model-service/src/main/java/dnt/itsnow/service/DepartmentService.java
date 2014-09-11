package dnt.itsnow.service;

import dnt.itsnow.Exception.DepartmentException;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>部门服务类</h1>
 */
public interface DepartmentService {

    public Page<Department> findAll(String keyword, Pageable pageable);

    public Department findBySn(String sn);

    public Department create(Department department) throws DepartmentException;

    public Department update(Department department) throws DepartmentException;

    public Department destroy(Department department) throws DepartmentException;

}
