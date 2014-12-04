package dnt.itsnow.support;

import dnt.itsnow.exception.SiteException;
import dnt.itsnow.model.Department;
import dnt.itsnow.model.Site;
import dnt.itsnow.model.SiteDept;
import net.happyonroad.platform.service.AutoNumberService;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.service.Pageable;
import net.happyonroad.platform.util.DefaultPage;
import dnt.itsnow.repository.SiteDeptRepository;
import dnt.itsnow.repository.SiteRepository;
import dnt.itsnow.service.SiteService;
import net.happyonroad.spring.Bean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * <h1>地点管理业务实现类</h1>
 */
@Service
@Transactional
public class SiteManager extends Bean implements SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SiteDeptRepository siteDeptRepository;

    @Autowired
    private AutoNumberService autoNumberService;

    @Override
    public Page<Site> findAll(String keyword, Pageable pageable) {

        logger.debug("Finding sites by keyword: {}", keyword);

        int total = siteRepository.count(keyword);
        List<Site> sites = siteRepository.findAll(keyword, pageable);
        DefaultPage<Site> page = new DefaultPage<Site>(sites, pageable, total);

        logger.debug("Found   {}", page.getContent());

        return page;
    }

    @Override
    public Site findBySn(String sn) {
        logger.debug("Finding Site by sn: {}", sn);
        Site site = siteRepository.findBySn(sn);
        logger.debug("Found   Site by sn: {}", sn);
        return site;
    }

    @Override
    public Site create(Site site) throws SiteException {

        logger.info("Creating {}", site);

        if (site == null) {
            throw new SiteException("Site entry can not be empty.");
        }
        autoNumberService.configure("site", "");
        site.setSn(autoNumberService.next("site"));
        site.setSn(UUID.randomUUID().toString());
        site.creating();
        siteRepository.create(site);

        if (site.getDepartments() != null) {
            for (Department department : site.getDepartments()) {
                SiteDept siteDept = new SiteDept(site, department);

                if (siteDeptRepository.find(siteDept) == null) {
                    siteDeptRepository.create(siteDept);
                }
            }
        }

        logger.info("Created  {}", site);

        return site;
    }

    @Override
    public Site update(Site site) throws SiteException {

        logger.info("Updating {}", site);

        if (site == null) {
            throw new SiteException("Site entry can not be empty.");
        }
        site.updating();
        siteRepository.update(site);

        siteDeptRepository.deleteSiteAndDeptRelationBySiteId(site.getId());
        for (Department department : site.getDepartments()) {
            siteDeptRepository.create(new SiteDept(site, department));
        }

        logger.info("Updated  {}", site);

        return site;
    }

    @Override
    public void destroy(Site site) throws SiteException {

        logger.warn("Deleting {}", site);

        if (site == null) {
            throw new SiteException("Site entry can not be empty.");
        }
        siteDeptRepository.deleteSiteAndDeptRelationBySiteId(site.getId());
        siteRepository.delete(site.getSn());

        logger.warn("Deleted  {}", site);
    }

    @Override
    public Site findByName(String name) {

        logger.debug("Finding Site by name: {}", name);

        Site site = siteRepository.findByName(name);

        logger.debug("Found   {}", site);

        return site;
    }
}
