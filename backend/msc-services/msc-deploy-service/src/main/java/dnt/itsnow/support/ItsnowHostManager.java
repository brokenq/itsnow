/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.service.ItsnowHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <h1>Itsnow Host Manager</h1>
 */
@Service
public class ItsnowHostManager extends ItsnowResourceManager implements ItsnowHostService {
    @Autowired
    ItsnowHostRepository       repository;

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
        SystemInvocation configJob = translator.config(creating);
        //需要在create主机之后，执行脚本，将主机环境配置好
        // 实际的流程是，it，运营人员开好一个虚拟机，而后通过msc的界面输入该虚拟机的信息
        // 通过调用本api创建itsnow的主机，而后本api就会自动配置该主机
        // 配置的环境内容包括: java, mysql, redis, msc, msu, msp的部署
        invokeService.addJob(configJob);
        // 这里不等待该任务结束，因为configure主机可能时间很长
        // 采用主机状态管理方式，也就是刚刚创建的主机处于configure状态，configure好了之后处于ready状态
        creating.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        creating.setUpdatedAt(creating.getCreatedAt());
        repository.create(creating);
        logger.info("Created  host {}", creating);
        return creating;
    }

    @Override
    public void delete(ItsnowHost host) throws ItsnowHostException {
        SystemInvocation quitJob = translator.quit(host);
        String quitJobId = invokeService.addJob(quitJob);
        try {
            invokeService.waitJobFinished(quitJobId);
        } catch (SystemInvokeException e) {
            throw new ItsnowHostException("Can't quit host for " + host);
        }
        //TODO 如果有外键引用，会被拒绝，应该将底层的异常转换为合适的ItsnowHostException
        // 能够通过异常很容易的告知用户host被哪个业务对象所引用
        try {
            repository.deleteByAddress(host.getAddress());
        } catch (Exception e) {
            throw new ItsnowHostException("Can't delete itsnow host: " + host, e);
        }
    }
}
