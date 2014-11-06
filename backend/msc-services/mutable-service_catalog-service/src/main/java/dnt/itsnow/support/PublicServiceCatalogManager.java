package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.repository.PublicServiceCatalogRepository;
import dnt.itsnow.service.PublicServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * <h1>Public ServiceCatalog Manager</h1>
 */
@Service
@Transactional
public class PublicServiceCatalogManager extends CommonServiceCatalogManager implements PublicServiceCatalogService {

    @Autowired
    PublicServiceCatalogRepository publicServiceCatalogRepository;

    @Override
    public PublicServiceCatalog create(PublicServiceCatalog publicServiceCatalog) {
        setCommonServiceCatalogList(null);
        setFormattedServiceCatalogList(null);
        publicServiceCatalog.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        publicServiceCatalog.setUpdatedAt(publicServiceCatalog.getCreatedAt());
        publicServiceCatalogRepository.create(publicServiceCatalog);
        return publicServiceCatalog;
    }

    @Override
    public PublicServiceCatalog update(PublicServiceCatalog publicServiceCatalog) {
        setCommonServiceCatalogList(null);
        setFormattedServiceCatalogList(null);
        publicServiceCatalog.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        publicServiceCatalogRepository.update(publicServiceCatalog);
        return publicServiceCatalog;
    }

    @Override
    public void delete(PublicServiceCatalog publicServiceCatalog) {
        setCommonServiceCatalogList(null);
        setFormattedServiceCatalogList(null);
        publicServiceCatalogRepository.delete(publicServiceCatalog.getSn());
    }

}
