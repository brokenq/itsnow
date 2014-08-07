/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.repository.CommonContractRepository;

/**
 * <h1>Common Contract Service Configuration</h1>
 *
 * which export Contract Repository
 */
public class CommonContractServiceConfig extends DefaultCommonServiceConfig{
    @Override
    public void defineServices() {
        super.defineServices();
        exportService(CommonContractRepository.class);
    }
}
