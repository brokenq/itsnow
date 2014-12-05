package dnt.itsnow.support;

import dnt.itsnow.model.PublicServiceCatalog;
import dnt.itsnow.repository.PublicServiceCatalogRepository;
import dnt.itsnow.repository.PublicServiceItemRepository;
import dnt.itsnow.service.PublicServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Public ServiceCatalog Manager</h1>
 */
@Service
@Transactional
public class PublicServiceCatalogManager extends CommonServiceCatalogManager implements PublicServiceCatalogService {

    @Autowired
    PublicServiceCatalogRepository publicServiceCatalogRepository;

    @Autowired
    PublicServiceItemRepository publicServiceItemRepository;

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

    @Override
    public void saveByAccount(PublicServiceCatalog catalog, Long accountId) {
        long count = publicServiceCatalogRepository.countByAccountAndCatalog(accountId,catalog.getId());
        if(count == 0)
            publicServiceCatalogRepository.addByAccount(catalog.getId(),accountId);
        if(catalog.getParentId() != null){
            catalog = this.findById(catalog.getParentId());
            if(catalog != null)
                saveByAccount(catalog,accountId);
        }
    }

    @Override
    public void deleteByAccount(PublicServiceCatalog catalog, Long accountId) {
        //find all catalog ids
        Set<Long> ids = new HashSet<Long>();
        ids.add(catalog.getId());
        Set<Long> temp = publicServiceCatalogRepository.findIdsByParentIds(ids);
        while(temp!=null && !temp.isEmpty()){
            ids.addAll(temp);
            temp = publicServiceCatalogRepository.findIdsByParentIds(temp);
        }

        //delete all catalog ids
        publicServiceCatalogRepository.deleteByAccount(ids,accountId);

        //find all item ids
        ids = publicServiceItemRepository.findIdsByCatalogIds(ids);
        //delete all item ids
        publicServiceItemRepository.deleteByAccount(ids,accountId);
    }

}
