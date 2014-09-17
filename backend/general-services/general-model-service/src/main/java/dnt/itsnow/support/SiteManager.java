package dnt.itsnow.support;

import dnt.itsnow.exception.SiteException;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import dnt.itsnow.model.SiteDept;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.repository.SiteDeptRepository;
import dnt.itsnow.repository.SiteRepository;
import dnt.itsnow.service.SiteService;
import dnt.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>地点业务实现类</h1>
 */
@Service
@Transactional
public class SiteManager extends Bean implements SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SiteDeptRepository siteDeptRepository;

    @Override
    public Page<Site> findAll(String keyword, Pageable pageable) {
        logger.debug("Finding site by keyword: {}", keyword);

        if(StringUtils.isBlank(keyword)){
            int total = siteRepository.count();
            List<Site> sites = siteRepository.findAll("updated_at", "desc", pageable.getOffset(), pageable.getPageSize());
            logger.debug("Finded site by keyword: {}", keyword);
            return new DefaultPage<Site>(sites, pageable, total);
        }else{
            int total = siteRepository.countByKeyword("%"+keyword+"%");
            List<Site> sites = siteRepository.findAllByKeyword("%"+keyword+"%","updated_at","desc", pageable.getOffset(), pageable.getPageSize());
            logger.debug("Finded site by keyword: {}", keyword);
            return new DefaultPage<Site>(sites, pageable, total);
        }
    }

    @Override
    public Site findBySn(String sn) {
        logger.debug("Finding Site by sn: {}", sn);
        Site site = siteRepository.findBySn(sn);
        logger.debug("Finded Site by sn: {}", sn);
        return site;
    }

    @Override
    public Site create(Site site) throws SiteException {
        logger.info("Creating site {}", site);
        if(site == null){
            throw new SiteException("Site entry can not be empty.");
        }
        site.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        site.setUpdatedAt(site.getCreatedAt());
        siteRepository.create(site);

        for(Department department : site.getDepartments()){
            siteDeptRepository.create(new SiteDept(site, department));
        }
        logger.info("Created site {}", site);
        return site;
    }

    @Override
    public Site update(Site site) throws SiteException {
        logger.info("Updating site {}", site);
        if(site==null){
            throw new SiteException("Site entry can not be empty.");
        }
        siteRepository.update(site);

        siteDeptRepository.deleteSiteAndDeptRelationBySiteId(site.getId());
        for(Department department : site.getDepartments()){
            siteDeptRepository.create(new SiteDept(site, department));
        }

        return site;
    }

    @Override
    public void destroy(Site site) throws SiteException {
        logger.warn("Deleting site {}", site);
        if(site==null){
            throw new SiteException("Site entry can not be empty.");
        }
        siteDeptRepository.deleteSiteAndDeptRelationBySiteId(site.getId());
        siteRepository.delete(site.getSn());
    }
}
