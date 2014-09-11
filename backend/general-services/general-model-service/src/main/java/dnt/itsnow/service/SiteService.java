package dnt.itsnow.service;

import dnt.itsnow.Exception.SiteException;
import dnt.itsnow.exception.MenuItemException;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

import java.util.List;

/**
 * <h1>类功能说明</h1>
 */
public interface SiteService {

    public Page<Site> findAll(String keyword, Pageable pageable);

    public Site findBySn(String sn);

    public Site create(Site site) throws SiteException;

    public Site update(Site site) throws SiteException;

    public Site destroy(Site site) throws SiteException;

}
