package dnt.itsnow.support;

import dnt.itsnow.exception.StaffException;
import dnt.itsnow.model.Staff;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.StaffRepository;
import dnt.itsnow.service.StaffService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>员工管理业务实现层</h1>
 */
@Service
public class StaffManager extends Bean implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Page<Staff> findAll(String keyword, Pageable pageable) {

        logger.debug("Finding staffs by keyword: {}", keyword);

        int total = staffRepository.count(keyword);
        List<Staff> staffs = staffRepository.findAll(keyword, pageable);
        DefaultPage<Staff> page = new DefaultPage<Staff>(staffs, pageable, total);

        logger.debug("Found   staffs {}", page.getContent());

        return page;
    }

    @Override
    public Staff findByNo(String no) {

        logger.debug("Finding Staff by NO. : {}", no);

        Staff staff = staffRepository.findByNo(no);

        logger.debug("Found   {}", staff);

        return staff;
    }

    @Override
    public Staff create(Staff staff) throws StaffException {

        logger.info("Creating {}", staff);

        if (staff == null) {
            throw new StaffException("Staff entry can not be empty.");
        }
        staff.creating();
        staffRepository.create(staff);

        logger.info("Created  {}", staff);

        return staff;
    }

    @Override
    public Staff update(Staff staff) throws StaffException {

        logger.info("Updating {}", staff);

        if (staff == null) {
            throw new StaffException("Staff entry can not be empty.");
        }
        staff.updating();
        staffRepository.update(staff);

        logger.info("Updated  {}", staff);

        return staff;
    }

    @Override
    public Staff destroy(Staff staff) throws StaffException {

        logger.warn("Deleting {}", staff);

        if (staff == null) {
            throw new StaffException("Staff entry can not be empty.");
        }
        staffRepository.delete(staff.getNo());

        logger.warn("Deleted  {}", staff);

        return staff;
    }
}
