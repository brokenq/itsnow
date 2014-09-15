package dnt.itsnow.service;

import dnt.itsnow.Exception.StaffException;
import dnt.itsnow.model.Staff;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * <h1>类功能说明</h1>
 */
public interface StaffService {

    public Page<Staff> findAll(String keyword, Pageable pageable);

    public Staff findByNo(String no);

    public Staff create(Staff staff) throws StaffException;

    public Staff update(Staff staff) throws StaffException;

    public Staff destroy(Staff staff) throws StaffException;

}
