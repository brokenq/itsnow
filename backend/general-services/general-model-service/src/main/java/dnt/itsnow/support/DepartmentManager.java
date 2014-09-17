package dnt.itsnow.support;

import dnt.itsnow.exception.DepartmentException;
import dnt.itsnow.model.Department;
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

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public class DepartmentManager extends Bean implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SiteDeptRepository siteDeptRepository;

    @Override
    public Page<Department> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding department by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = departmentRepository.count();
            List<Department> departments = departmentRepository.find("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Department>(departments, pageable, total);
        }else{
            int total = departmentRepository.countByKeyword("%"+keyword+"%");
            List<Department> departments = departmentRepository.findByKeyword("%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Department>(departments, pageable, total);
        }
    }

    @Override
    public Department findBySn(String sn) {
        logger.debug("Finding Department by sn: {}", sn);

        return departmentRepository.findBySn(sn);
    }

    @Override
    public Department create(Department department) throws DepartmentException {
        logger.info("Creating department {}", department);
        if(department == null){
            throw new DepartmentException("Department entry can not be empty.");
        }
        department.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        department.setUpdatedAt(department.getCreatedAt());
        departmentRepository.create(department);

        for(Site site : department.getSites()){
            siteDeptRepository.create(new SiteDept(site, department));
        }

        return department;
    }

    @Override
    public Department update(Department department) throws DepartmentException {
        logger.info("Updating department {}", department);
        if(department==null){
            throw new DepartmentException("Department entry can not be empty.");
        }
        departmentRepository.update(department);

        for(Site site : department.getSites()){
            siteDeptRepository.create(new SiteDept(site, department));
        }

        return department;
    }

    @Override
    public Department destroy(Department department) throws DepartmentException {
        logger.warn("Deleting department {}", department);
        if(department==null){
            throw new DepartmentException("Department entry can not be empty.");
        }
        siteDeptRepository.deleteDeptAndSiteRelationByDeptId(department.getId());
        departmentRepository.delete(department.getSn());
        return department;
    }
}
