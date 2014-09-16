/**
 * Developer: Kadvin Date: 14-9-15 上午10:44
 */
package dnt.itsnow.repository;

import dnt.itsnow.model.ItsnowSchema;

/**
 * <h1>Itsnow Schema Repository</h1>
 */
public interface ItsnowSchemaRepository {
    void create(ItsnowSchema schema);

    void delete(ItsnowSchema schema);

    ItsnowSchema findByName(String name);
}
