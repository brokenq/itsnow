/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.HostStatus;
import dnt.itsnow.model.ItsnowHost;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.service.ItsnowHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Itsnow Host Manager</h1>
 */
@Service
@Transactional
public class ItsnowHostManager extends ItsnowResourceManager implements ItsnowHostService {

    @Autowired
    ItsnowHostRepository repository;

    @Override
    public Page<ItsnowHost> findAll(String keyword, PageRequest pageRequest) {
        logger.debug("Listing itsnow hosts by keyword: {} at {}", keyword, pageRequest);
        int total = repository.countByKeyword(keyword);
        List<ItsnowHost> hits;
        if( total == 0 ){
            hits = new ArrayList<ItsnowHost>();
        }else{
            hits = repository.findAllByKeyword(keyword, pageRequest);
        }
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

        SystemInvocation configJob = translator.provision(creating);
        configJob.setUserFlag(1);// 1 代表创建
        //需要在create主机之后，执行脚本，将主机环境配置好
        // 实际的流程是，it，运营人员开好一个虚拟机，而后通过msc的界面输入该虚拟机的信息
        // 通过调用本api创建itsnow的主机，而后本api就会自动配置该主机
        // 配置的环境内容包括: java, mysql, redis, msc, msu, msp的部署
        // 这里不等待该任务结束，因为configure主机可能时间很长
        // 采用主机状态管理方式，也就是刚刚创建的主机处于configure状态，configure好了之后处于ready状态
        String invocationId = invokeService.addJob(configJob);
        creating.setProperty(CREATE_INVOCATION_ID, invocationId);
        creating.creating();
        repository.create(creating);
        logger.info("Created  host {}", creating);
        return creating;
    }


    @Override
    public void delete(ItsnowHost host) throws ItsnowHostException {
        logger.warn("Deleting {}", host);
        SystemInvocation delistJob = translator.delist(host);
        delistJob.setUserFlag(-1);
        String delistJobId = invokeService.addJob(delistJob);
        host.setProperty(DELETE_INVOCATION_ID, delistJobId);
        host.updating();
        repository.update(host);
        // 因为主机下线操作比较快，所以采用同步方式
        try {
            invokeService.waitJobFinished(delistJobId);
        } catch (SystemInvokeException e) {
            throw new ItsnowHostException("Can't delist host " + host, e);
        }
        //TODO 如果有外键引用，会被拒绝，应该将底层的异常转换为合适的ItsnowHostException
        // 能够通过异常很容易的告知用户host被哪个业务对象所引用
        try {
            repository.deleteByAddress(host.getAddress());
        } catch (Exception e) {
            throw new ItsnowHostException("Can't delete itsnow host: " + host, e);
        }
        logger.warn("Deleted  {}", host);
    }

    @Override
    public long follow(ItsnowHost host, String jobId, long offset, List<String> result) {
        logger.trace("Follow {}'s job: {}", host, jobId);
        return invokeService.read(jobId, offset, result);
    }

    //////////////////////////////////////////////////////////////////////////////
    // 监听 主机配置任务的执行情况，并通过repository更新相应的host状态
    //////////////////////////////////////////////////////////////////////////////

    @Override
    public void finished(SystemInvocation invocation) {
        super.finished(invocation);
        whenInvocationDone(invocation, HostStatus.Running);
    }

    @Override
    public void cancelled(SystemInvocation invocation) {
        super.cancelled(invocation);
        whenInvocationDone(invocation, HostStatus.Abnormal);
    }

    @Override
    public void failed(SystemInvocation invocation, SystemInvokeException e) {
        super.failed(invocation, e);
        whenInvocationDone(invocation, HostStatus.Abnormal);
    }

    protected void whenInvocationDone(SystemInvocation invocation, HostStatus status) {
        ItsnowHost host;
        if( invocation.getUserFlag() == 1){
            host = getHostByInvocationId(CREATE_INVOCATION_ID, invocation.getId());
        }else{
            return;// 马上就要删除了，没必要再更新状态
        }
        if( host == null ) return;//听到了别的消息，忽略
        logger.info("Update {} status as {}", host, status);
        host.setStatus(status);
        host.updating();
        repository.update(host);
    }

    ItsnowHost getHostByInvocationId(String propertyName, String propertyValue) {
        return repository.findByConfiguration(propertyName, propertyValue);
    }

}
