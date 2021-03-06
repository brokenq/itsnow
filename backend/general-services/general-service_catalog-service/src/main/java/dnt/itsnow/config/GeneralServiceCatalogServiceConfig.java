/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import org.springframework.web.client.RestOperations;

/**
 * <h1>The general service_catalog service config</h1>
 */
public class GeneralServiceCatalogServiceConfig extends DefaultGeneralServiceConfig {

    @Override
    public void defineServices() {
        super.defineServices();
        //importService(CommonServiceCatalogService.class);
        importService(RestOperations.class);

    }

}
