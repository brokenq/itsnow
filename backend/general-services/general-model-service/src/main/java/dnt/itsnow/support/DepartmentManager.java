package dnt.itsnow.support;

import dnt.itsnow.exception.DepartmentException;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import dnt.itsnow.model.SiteDept;
import dnt.itsnow.repository.DepartmentRepository;
import dnt.itsnow.repository.SiteDeptRepository;
import dnt.itsnow.repository.SiteRepository;
import dnt.itsnow.service.DepartmentService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private SiteRepository sitRepository;

    @Override
    public List<Department> findAll(String keyword, boolean isTree) {

        logger.debug("Finding departments by keyword, show tree is {}", keyword, isTree);

        List<Department> departments = departmentRepository.findAll(keyword);
        if (isTree) {
            departments = buildTree(departments);
            departments = buildTreeTable(departments);
        }

        logger.debug("Found   {}", departments);

        return departments;
    }

    @Override
    public Department findBySn(String sn) {

        logger.debug("Finding Department by sn: {}", sn);

        Department department = departmentRepository.findBySn(sn);

        logger.debug("Found   {}", department);

        return department;
    }

    @Override
    public List<Department> findAllByParentId(long id) {

        logger.debug("Finding Department by id: {}", id);

        List<Department> departments = departmentRepository.findAllByParentId(id);

        logger.debug("Found   {}", departments);

        return departments;
    }

    @Override
    public Department findByName(String name) {

        logger.debug("Finding Department by name: {}", name);

        Department department = departmentRepository.findByName(name);

        logger.debug("Found   {}", department);

        return department;
    }

    @Override
    public Department create(Department department) throws DepartmentException {

        logger.info("Creating {}", department);

        if (department == null) {
            throw new DepartmentException("Department entry can not be empty.");
        }

        department.setSn(UUID.randomUUID().toString());
        department.setPosition(0L);
        department.creating();
        departmentRepository.create(department);

        for (Site site : department.getSites()) {

            site = sitRepository.findBySn(site.getSn());
            SiteDept siteDept = new SiteDept(site, department);

            if (siteDeptRepository.find(siteDept) == null) {
                siteDeptRepository.create(siteDept);
            }
        }

        logger.info("Created  {}", department);

        return department;
    }

    @Override
    public Department update(Department department) throws DepartmentException {

        logger.info("Updating {}", department);

        if (department == null) {
            throw new DepartmentException("Department entry can not be empty.");
        }

        department.updating();
        departmentRepository.update(department);

        siteDeptRepository.deleteDeptAndSiteRelationByDeptId(department.getId());
        for (Site site : department.getSites()) {
            site = sitRepository.findBySn(site.getSn());
            siteDeptRepository.create(new SiteDept(site, department));
        }

        logger.info("Updating   {}", department);

        return department;
    }

    @Override
    public void destroy(Department department) throws DepartmentException {

        logger.warn("Deleting {}", department);

        if (department == null) {
            throw new DepartmentException("Department entry can not be empty.");
        }
        loopDestroy(department);

        logger.warn("Deleted  {}", department);
    }

    private void loopDestroy(Department department) {
        List<Department> subDepartments = departmentRepository.findAllByParentId(department.getId());
        if (subDepartments != null && subDepartments.size() > 0) {
            for(Department dept : subDepartments){
                loopDestroy(dept);
            }
        }
        siteDeptRepository.deleteDeptAndSiteRelationByDeptId(department.getId());
        departmentRepository.delete(department.getSn());
        logger.warn("Deleted  {}", department);
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
        List<Department> rootDepartments = new ArrayList<Department>();
        for (Department dept : departments) {
            if (dept.getParentId() != null) {
                Department parent = map.get(dept.getParentId());
                dept.setParent(parent);
            } else {
                rootDepartments.add(dept);
            }
        }
        logger.debug("rootDepartments:{}", rootDepartments);
        Collections.sort(rootDepartments);
        return rootDepartments;
    }

    /**
     * 以tree table形式生成部门列表
     *
     * @param departments 树结构的部门列表
     * @return 以treeTable形式展示的部门列表
     */
    private List<Department> buildTreeTable(List<Department> departments) {
        List<Department> treeTable = new LinkedList<Department>();
        for (Department dept : departments) {
            treeTable.add(dept);
            loop(treeTable, dept, "");
        }
        return treeTable;
    }

    private void loop(List<Department> treeTable, Department dept, String symbol) {
        if (dept.getChildren() != null && dept.getChildren().size() > 0) {
            symbol += "──";
            for (Department child : dept.getChildren()) {
                child.setName(symbol + child.getName());
                treeTable.add(child);
                loop(treeTable, child, symbol);
            }
            dept.setChildren(null);
        }
    }

}
