/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.*;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.DefaultPage;
import dnt.itsnow.platform.util.PageRequest;
import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.service.ItsnowHostService;
import dnt.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <h1>Itsnow Host Manager</h1>
 */
@Service
@Transactional
public class ItsnowHostManager extends ItsnowResourceManager implements ItsnowHostService {
    static Pattern PATTERN = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");

    @Autowired
    ItsnowHostRepository repository;
    private String mscAddress;

    @Override
    public Page<ItsnowHost> findAll(String keyword, PageRequest pageRequest) {
        logger.debug("Listing itsnow hosts by keyword: {} at {}", keyword, pageRequest);
        int total = repository.countByKeyword(keyword);
        List<ItsnowHost> hits;
        if (total == 0) {
            hits = new ArrayList<ItsnowHost>();
        } else {
            if (StringUtils.isNotBlank(keyword)) keyword = "%" + keyword + "%";
            else keyword = null;
            hits = repository.findAllByKeyword(keyword, pageRequest);
        }
        DefaultPage<ItsnowHost> page = new DefaultPage<ItsnowHost>(hits, pageRequest, total);
        logger.debug("Listed  itsnow hosts: {}", page);
        return page;
    }

    @Override
    public List<ItsnowHost> findAllDbHosts() {
        logger.debug("Listing all itsnow hosts support mysql instance");
        List<ItsnowHost> dbHosts = repository.findAllByType(HostType.DB);
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
    public ItsnowHost findByName(String name) {
        logger.debug("Finding itsnow host by name: {}", name);
        ItsnowHost host = repository.findByName(name);
        logger.debug("Found   itsnow host: {}", host);
        return host;
    }

    @Override
    public String resolveAddress(String name) throws ItsnowHostException{
        logger.debug("Resolving host address of {}", name);
        String hostAddress;
        String appDomain = translator.getAppDomain();
        try {
            if(mscAddress == null ) mscAddress = initMscAddress();
        } catch (UnknownHostException e) {
            throw new ItsnowHostException("Failed to resolve msc host" , e);
        }
        try {
            hostAddress = InetAddress.getByName(name).getHostAddress();
        } catch (UnknownHostException e) {
            if( name.toLowerCase().endsWith("." + appDomain) )
                throw new ItsnowHostException("Failed to resolve host name: " + name, e);
            else
                return resolveAddress(name + "." + appDomain);
        }
        if(StringUtils.equals(mscAddress, hostAddress)){
            throw new ItsnowHostException("Bad host name: " + name);
        }
        logger.debug("Resolved  host address {}", hostAddress);
        return hostAddress;
    }

    private String initMscAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    @Override
    public String resolveName(String address) throws ItsnowHostException{
        logger.debug("Resolve host name of {}", address);
        if( !PATTERN.matcher(address).matches() )
            throw new IllegalArgumentException("Bad ip address: " + address);
        String hostName;
        try {
            hostName = InetAddress.getByName(address).getHostName();
        } catch (UnknownHostException e) {
            throw new ItsnowHostException("Failed to resolve host address: " + address, e);
        }
        return hostName;
    }

    @Override
    public boolean checkPassword(String host, String username, String password) throws ItsnowHostException {
        logger.debug("Checking {}@{} password availability", username, host);
        SystemInvocation checkJob = translator.checkHostUser(host, username, password);
        String jobId = invokeService.addJob(checkJob);
        try {
            int code = invokeService.waitJobFinished(jobId);
            return code == 0;
        } catch (SystemInvokeException e) {
            throw new ItsnowHostException("Checking " + host + " password availability " , e);
        }
    }

    @Override
    public List<ItsnowHost> findByType(String type) {
        logger.debug("Finding itsnow host by type = {}", type);
        List<ItsnowHost> hosts = repository.findAllByType(HostType.valueOf(type.toUpperCase()));
        logger.debug("Found size of itsnow host is {}", hosts.size());
        return hosts;
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
        if( creating.getProperty("mysql.slave.index") == null ){
            int total = repository.countByKeyword(null);
            creating.setProperty("mysql.slave.index", String.valueOf(total+1) );
        }
        SystemInvocation configJob = translator.provision(creating);
        configJob.setId("provision-" + creating.getAddress());
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
        creating.setStatus(HostStatus.Planing);
        repository.create(creating);
        logger.info("Created  host {}", creating);
        return creating;
    }


    @Override
    public void delete(ItsnowHost host) throws ItsnowHostException {
        logger.warn("Deleting {}", host);
        SystemInvocation delistJob = translator.delist(host);
        delistJob.setId("delist-" + host.getAddress());
        delistJob.setUserFlag(-1);
        String delistJobId = invokeService.addJob(delistJob);
        host.setProperty(DELETE_INVOCATION_ID, delistJobId);
        host.updating();
        host.setStatus(HostStatus.Delisting);
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
    public void update(ItsnowHost host) throws ItsnowHostException {
        logger.info("Updating {}", host);
        host.updating();
        repository.update(host);
        logger.info("Updated  {}", host);
    }

    @Override
    public long follow(ItsnowHost host, String jobId, long offset, List<String> result) {
        logger.trace("Follow {}'s job: {}", host, jobId);
        if(jobId.equals(host.getProperty(CREATE_INVOCATION_ID)) && host.getStatus() == HostStatus.Planing){
            return invokeService.read(jobId, offset, result);
        }else if (jobId.equals(host.getProperty(DELETE_INVOCATION_ID)) && host.getStatus() == HostStatus.Delisting){
            return invokeService.read(jobId, offset, result);
        }
        return -1;
    }

    @Override
    public ItsnowHost pickHost(Account account, HostType type) throws ItsnowHostException {
        List<ItsnowHost> hostList = repository.findAllByType(type);
        List<ItsnowHost> combineList = repository.findAllByType(HostType.COM);
        hostList.addAll(combineList);
        if(hostList.isEmpty())
            throw new ItsnowHostException("There is not " + type + " host available for " + account);
        ItsnowHost[] hosts = hostList.toArray(new ItsnowHost[hostList.size()]);
        Arrays.sort(hosts);
        return hosts[hosts.length-1];
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
