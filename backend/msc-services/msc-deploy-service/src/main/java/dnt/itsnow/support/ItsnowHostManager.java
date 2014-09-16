/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.service.ItsnowHostService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>Itsnow Host Manager</h1>
 */
@Service
public class ItsnowHostManager extends Bean implements ItsnowHostService {
    @Autowired
    ItsnowHostRepository repository;

    @Override
    public Page<ItsnowHost> findAll(String keyword, PageRequest pageRequest) {
        logger.debug("Listing itsnow hosts by keyword: {} at {}", keyword, pageRequest);
        int total = repository.countByKeyword(keyword);
        List<ItsnowHost> hits = repository.findAllByKeyword(keyword, pageRequest);
        DefaultPage<ItsnowHost> page = new DefaultPage<ItsnowHost>(hits, pageRequest, total);
        logger.debug("Listed  itsnow hosts: {}", page);
        return page;
    }

    @Override
    public List<ItsnowHost> findAllDbHosts() {
        logger.debug("Listing all itsnow hosts support mysql instance");
        List<ItsnowHost> dbHosts = repository.findAllDbHosts();
        logger.debug("Listed  {} db hosts", dbHosts.size());
        return dbHosts;
    }

    @Override
    public ItsnowHost findByAddress(String address) {
        logger.debug("Finding itsnow host by address: {}", address);
        ItsnowHost host = repository.findByAddress(address);
        logger.debug("Found   itsnow host: {}", host);
        return host;
    }

    @Override
    public ItsnowHost findById(Long hostId) {
        logger.debug("Finding itsnow host by id: {}", hostId);
        ItsnowHost host = repository.findById(hostId);
        logger.debug("Found   itsnow host: {}", host);
        return host;
    }

    @Override
    public ItsnowHost create(ItsnowHost creating) throws ItsnowHostException {
        logger.info("Creating host {}", creating);
        creating.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        creating.setUpdatedAt(creating.getCreatedAt());
        repository.create(creating);
        logger.info("Created  host {}", creating);
        return creating;
    }

    @Override
    public void delete(String address) throws ItsnowHostException {
        //如果有外键引用，会被拒绝
        try {
            repository.deleteByAddress(address);
        } catch (Exception e) {
            throw new ItsnowHostException("Can't delete itsnow host: " + address, e);
        }
    }
}
