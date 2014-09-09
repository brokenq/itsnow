package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.repository.PublicServiceCatalogRepository;
import dnt.itsnow.service.PublicServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by jacky on 2014/9/2.
 */
@Service
public class PublicServiceCatalogManager extends CommonServiceCatalogManager implements PublicServiceCatalogService {

    @Autowired
    PublicServiceCatalogRepository publicServiceCatalogRepository;

    @Override
    public PublicServiceCatalog create(PublicServiceCatalog publicServiceCatalog) {
        setCommonServiceCatalogList(null);
        publicServiceCatalog.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        publicServiceCatalog.setUpdatedAt(publicServiceCatalog.getCreatedAt());
        publicServiceCatalogRepository.create(publicServiceCatalog);
        return publicServiceCatalog;
    }

    @Override
    public PublicServiceCatalog update(PublicServiceCatalog publicServiceCatalog) {
        setCommonServiceCatalogList(null);
        publicServiceCatalog.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        publicServiceCatalogRepository.update(publicServiceCatalog);
        return publicServiceCatalog;
    }

    @Override
    public void delete(PublicServiceCatalog publicServiceCatalog) {
        setCommonServiceCatalogList(null);
        publicServiceCatalogRepository.delete(publicServiceCatalog.getSn());
    }

}
