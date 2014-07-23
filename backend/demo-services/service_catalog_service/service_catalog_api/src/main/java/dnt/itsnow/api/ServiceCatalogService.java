/**
 * Developer: Kadvin Date: 14-7-10 下午9:06
 */
package dnt.itsnow.api;

import dnt.itsnow.model.ServiceCatalog;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;

/**
 * The ServiceCatalog Service
 */
public interface ServiceCatalogService {

    ServiceCatalog find(Integer id);

    ServiceCatalog findByName(String scName);

    void save(ServiceCatalog serviceCatalog);

    void update(ServiceCatalog serviceCatalog);

    void delete(Integer id);

    Page<ServiceCatalog> findAll(String keyword, Pageable pageable);
}
