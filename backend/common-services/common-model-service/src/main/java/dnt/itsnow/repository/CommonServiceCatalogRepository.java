package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceCatalog;

import java.util.List;

/**
 * <h1>CommonServiceCatalog Repository</h1>
 */
public interface CommonServiceCatalogRepository {

    List<PublicServiceCatalog> findAll();

    PublicServiceCatalog findBySn(String sn);
}
