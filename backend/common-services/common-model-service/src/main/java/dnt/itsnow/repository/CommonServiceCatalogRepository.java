package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceCatalog;

import java.util.List;

/**
 * Created by jacky on 2014/9/2.
 */
public interface CommonServiceCatalogRepository {

    List<PublicServiceCatalog> findAll();

    PublicServiceCatalog findBySn(String sn);
}
