package dnt.itsnow.support;

import dnt.itsnow.exception.StaffException;
import dnt.itsnow.model.Staff;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.StaffRepository;
import dnt.itsnow.service.StaffService;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public class StaffManager extends Bean implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Page<Staff> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding staff by keyword: {}", keyword);
        if(StringUtils.isBlank(keyword)){
            int total = staffRepository.count();
            List<Staff> staffs = staffRepository.find("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Staff>(staffs, pageable, total);
        }else{
            int total = staffRepository.countByKeyword("%"+keyword+"%");
            List<Staff> staffs = staffRepository.findByKeyword("%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<Staff>(staffs, pageable, total);
        }
    }

    @Override
    public Staff findByNo(String no) {
        logger.debug("Finding Staff by no: {}", no);

        return staffRepository.findByNo(no);
    }

    @Override
    public Staff create(Staff staff) throws StaffException {
        logger.info("Creating staff {}", staff);
        if(staff == null){
            throw new StaffException("Staff entry can not be empty.");
        }
        staff.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        staff.setUpdatedAt(staff.getCreatedAt());
        staffRepository.create(staff);

        return staff;
    }

    @Override
    public Staff update(Staff staff) throws StaffException {
        logger.info("Updating staff {}", staff);
        if(staff==null){
            throw new StaffException("Staff entry can not be empty.");
        }
        staffRepository.update(staff);

        return staff;
    }

    @Override
    public Staff destroy(Staff staff) throws StaffException {
        logger.warn("Deleting staff {}", staff);
        if(staff==null){
            throw new StaffException("Staff entry can not be empty.");
        }
        staffRepository.delete(staff.getNo());
        return staff;
    }
}
