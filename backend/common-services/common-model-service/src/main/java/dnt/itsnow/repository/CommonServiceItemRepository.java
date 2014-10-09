package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceItem;

import java.util.List;

/**
 * <h1>CommonServiceItem Repository</h1>
 */
public interface CommonServiceItemRepository {

    List<PublicServiceItem> findAll();

    PublicServiceItem findById(Long id);

    List<PublicServiceItem> findByAccountId(Long accountId);
}
