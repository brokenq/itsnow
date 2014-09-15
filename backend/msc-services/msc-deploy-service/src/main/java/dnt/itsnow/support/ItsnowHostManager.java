/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.service.ItsnowHostService;
import dnt.spring.Bean;
import org.springframework.stereotype.Service;

/**
 * <h1>Itsnow Host Manager</h1>
 */
@Service
public class ItsnowHostManager extends Bean implements ItsnowHostService {
    @Override
    public Page<ItsnowHost> findAll(String keyword, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<ItsnowHost> findAllDbHosts() {
        return null;
    }

    @Override
    public ItsnowHost findByAddress(String address) {
        return null;
    }

    @Override
    public ItsnowHost create(ItsnowHost creating) throws ItsnowHostException {
        return null;
    }

    @Override
    public void delete(String address) throws ItsnowHostException {

    }
}
