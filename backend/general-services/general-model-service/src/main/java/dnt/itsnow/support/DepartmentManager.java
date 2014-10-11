package dnt.itsnow.support;

import dnt.itsnow.exception.DepartmentException;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.MenuItem;
import dnt.itsnow.model.Site;
import dnt.itsnow.model.SiteDept;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.DepartmentRepository;
import dnt.itsnow.repository.SiteDeptRepository;
import dnt.itsnow.service.DepartmentService;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * <h1>部门管理业务实现层</h1>
 */
@Service
public class DepartmentManager extends Bean implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SiteDeptRepository siteDeptRepository;

    @Override
    public List<Department> findAll(boolean isTree) {

        logger.debug("Finding departments");

        List<Department> departments = departmentRepository.findAll();
        if (isTree) {
            departments = buildTree(departments);
        }

        logger.debug("Found department list info : {}", departments);

        return departments;
    }

    @Override
    public Department findBySn(String sn) {

        logger.debug("Finding Department by sn: {}", sn);

        Department department = departmentRepository.findBySn(sn);

        logger.debug("Found Department : {}", department);

        return department;
    }

    @Override
    public Department create(Department department) throws DepartmentException {

        logger.info("Creating department {}", department);

        if (department == null) {
            throw new DepartmentException("Department entry can not be empty.");
        }

        department.creating();
        departmentRepository.create(department);

        for (Site site : department.getSites()) {
            SiteDept siteDept = new SiteDept(site, department);

            if (siteDeptRepository.find(siteDept) == null) {
                siteDeptRepository.create(siteDept);
            }
        }

        logger.info("Created department {}", department);

        return department;
    }

    @Override
    public Department update(Department department) throws DepartmentException {

        logger.info("Updating department {}", department);

        if (department == null) {
            throw new DepartmentException("Department entry can not be empty.");
        }

        department.updating();
        departmentRepository.update(department);

        siteDeptRepository.deleteDeptAndSiteRelationByDeptId(department.getId());
        for (Site site : department.getSites()) {
            siteDeptRepository.create(new SiteDept(site, department));
        }

        logger.info("Updating department {}", department);

        return department;
    }

    @Override
    public void destroy(Department department) throws DepartmentException {

        logger.warn("Deleting department {}", department);

        if (department == null) {
            throw new DepartmentException("Department entry can not be empty.");
        }
        siteDeptRepository.deleteDeptAndSiteRelationByDeptId(department.getId());
        departmentRepository.delete(department.getSn());

        logger.warn("Deleted department {}", department);
    }

    /**
     * 生成部门树
     *
     * @param departments 部门集合
     * @return 部门树形集合
     */
    private List<Department> buildTree(List<Department> departments) {
        Map<Long, Department> map = new HashMap<Long, Department>();
        for (Department dept : departments) {
            map.put(dept.getId(), dept);
        }
        List<Department> topDepartments = new ArrayList<Department>();
        for (Department dept : departments) {
            if (dept.getParentId() != null) {
                Department parent = map.get(dept.getParentId());
                dept.setParent(parent);
            } else {
                topDepartments.add(dept);
            }
        }
        Collections.sort(topDepartments);
        return topDepartments;
    }

}
