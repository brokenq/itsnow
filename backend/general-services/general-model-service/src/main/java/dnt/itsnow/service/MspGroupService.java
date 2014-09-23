package dnt.itsnow.service;

import dnt.itsnow.exception.GroupException;
import dnt.itsnow.model.MspGroup;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * <h1>Service</h1>
 */
public interface MspGroupService {

    public Page<MspGroup> findAll(String keyword, Pageable pageable);

    public Page<MspGroup> findAllRelevantInfo(String keyword, Pageable pageable);

    public MspGroup findBySn(String sn);

    public MspGroup create(MspGroup group) throws GroupException;

    public MspGroup update(MspGroup group) throws GroupException;

    public MspGroup destroy(MspGroup group) throws GroupException;

    List<MspGroup> search(String keyword);

    MspGroup find(String name);

}
