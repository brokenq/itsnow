package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceItem;

import java.util.List;

/**
 * <h1>CommonServiceItem Repository</h1>
 */
public interface CommonServiceItemRepository {

    List<PublicServiceItem> findAll();

    PublicServiceItem findBySn(String sn);

    List<PublicServiceItem> findByAccountId(Long accountId);
}
