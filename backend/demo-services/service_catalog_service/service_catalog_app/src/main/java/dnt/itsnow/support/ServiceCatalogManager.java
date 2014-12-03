/**
 * Developer: Kadvin Date: 14-7-10 下午9:07
 */
package dnt.itsnow.support;

import net.happyonroad.platform.util.DefaultPage;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.service.Pageable;
import dnt.itsnow.api.ServiceCatalogService;
import dnt.itsnow.model.ServiceCatalog;
import dnt.itsnow.repository.ServiceCatalogRepository;
import net.happyonroad.spring.Bean;
import dnt.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Session Service Implementations
 */
@Service
public class ServiceCatalogManager extends Bean implements ServiceCatalogService{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ServiceCatalogRepository repository;

    @Override
    public ServiceCatalog find(Integer id) {
        return repository.findById(id);
    }

    @Override
    public ServiceCatalog findByName(String scName) {
        return repository.findByName(scName);
    }

    @Override
    public void save(ServiceCatalog serviceCatalog) {
        repository.save(serviceCatalog);
    }

    @Override
    public void update(ServiceCatalog serviceCatalog) {
        repository.update(serviceCatalog);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    public Page<ServiceCatalog> findAll(String keyword, Pageable pageable){
        if(StringUtils.isBlank(keyword)){
            int total = repository.count();
            List<ServiceCatalog> scs = repository.findServiceCatalogs("updated_at desc", pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<ServiceCatalog>(scs, pageable, total);
        }else{
            int total = repository.countByKeyword(keyword);
            List<ServiceCatalog> scs = repository.findServiceCatalogsByKeyword(keyword, "updated_at desc",
                    pageable.getOffset(), pageable.getPageSize());
            return new DefaultPage<ServiceCatalog>(scs, pageable, total);
        }
    }
}
