package dnt.itsnow.repository;

import dnt.itsnow.model.PublicServiceItem;

import java.util.List;

/**
 * Created by jacky on 2014/9/2.
 */
public interface CommonServiceItemRepository {

    List<PublicServiceItem> findAll();

    PublicServiceItem findById(Long id);
}
