/**
 * xiongjie on 14-7-31.
 */
package dnt.itsnow.config;

import dnt.itsnow.repository.ContractRepository;

/**
 * <h1>General Contract Service Configuration</h1>
 *
 * which import Contract Repository provided by common contract service
 */
public class GeneralContractServiceConfig extends DefaultGeneralServiceConfig {
    @Override
    public void defineServices() {
        super.defineServices();
        importService(ContractRepository.class);
    }
}
