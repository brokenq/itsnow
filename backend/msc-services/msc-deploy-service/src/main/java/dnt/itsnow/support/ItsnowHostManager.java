/**
 * Developer: Kadvin Date: 14-9-15 上午10:47
 */
package dnt.itsnow.support;

import dnt.itsnow.exception.ItsnowHostException;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.*;
import net.happyonroad.platform.service.Page;
import net.happyonroad.platform.util.DefaultPage;
import net.happyonroad.platform.util.PageRequest;
import dnt.itsnow.repository.ItsnowHostRepository;
import dnt.itsnow.service.ItsnowHostService;
import net.happyonroad.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ItsnowHostManager extends ItsnowResourceManager implements ItsnowHostService {
    private static final String TRUST_HOST_PREFIX = "setup-trust-";
    private static final String PROVISION_HOST    = "provision-host-";
    private static final String DELIST_HOST       = "delist-host-";

    static Pattern PATTERN = Pattern.compile("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$");

    @Autowired
    ItsnowHostRepository repository;

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
    public List<ItsnowHost> findAll() {
        logger.debug("Finding all hosts");
        List<ItsnowHost> hosts = repository.findAll();
        logger.debug("Found   all hosts: {}", hosts.size());
        return hosts;
    }

    @Override
    public ItsnowHost findByAddress(String address) {
        logger.debug("Finding itsnow host by address: {}", address);
        ItsnowHost host = repository.findByAddress(address);
        logger.debug("Found   itsnow host: {}", host);
        return host;
    }

    @Override
    public ItsnowHost findByIdAndAddress(Long id, String address) {
        logger.debug("Finding itsnow host by id: {} and address: {}", id, address);
        ItsnowHost host = repository.findByIdAndAddress(id, address);
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
    public ItsnowHost findByIdAndName(Long id, String name) {
        logger.debug("Finding itsnow host by id: {} and name: {}", id, name);
        ItsnowHost host = repository.findByIdAndName(id, name);
        logger.debug("Found   itsnow host: {}", host);
        return host;
    }

    @Override
    public String resolveAddress(String name) throws ItsnowHostException{
        logger.debug("Resolving host address of {}", name);
        String hostAddress;
        String appDomain = translator.getAppDomain();
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
        checkJob.setId("check-" + username + "-password-of-" + host);
        String jobId = invokeService.addJob(checkJob);
        try {
            int code = invokeService.waitJobFinished(jobId);
            return code == 0;
        } catch (SystemInvokeException e) {
            throw new ItsnowHostException("Checking " + host + " password availability " , e);
        }
    }

    @Override
    public List<ItsnowHost> findAllByType(String type) {
        logger.debug("Finding itsnow host by type = {}", type);
        List<ItsnowHost> hosts = repository.findAllByType(HostType.valueOf(type.toUpperCase()));
        logger.debug("Found size of itsnow host is {}", hosts.size());
        return hosts;
    }

    @Override
    public void trustMe(String host, String username, String password) throws ItsnowHostException {
        logger.debug("Setting up trust relationship for target host: {}", host);
        SystemInvocation trustJob = translator.trustMe(host, username, password);
        trustJob.setId(TRUST_HOST_PREFIX + host);
        String jobId = invokeService.addJob(trustJob);
        int code;
        try {
            code = invokeService.waitJobFinished(jobId);
        } catch (SystemInvokeException e) {
            throw new ItsnowHostException("Checking " + host + " password availability " , e);
        }
        if (0 != code)
            throw new ItsnowHostException("Failed to setup trust relationship");
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
        configJob.setId(PROVISION_HOST + creating.getAddress());
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

    public ItsnowHost waitHostCreation(Long hostId, String jobId) throws ItsnowHostException {
        if( !invokeService.isFinished(jobId) ){
            try {
                invokeService.waitJobFinished(jobId);
            } catch (SystemInvokeException e) {
                throw new ItsnowHostException("Error while wait the job " + jobId + " finished");
            }
        }
        ItsnowHost host = repository.findById(hostId);
        if(host == null) throw new ItsnowHostException("The host id " + hostId+ " is invalid");
        if( host.getStatus() != HostStatus.Running){
            throw new ItsnowHostException("The host is not state in Running, but: " + host.getStatus());
        }
        return host;
    }

    @Override
    public List<ItsnowHost> findAllAvailableByType(List<String> types) throws ItsnowHostException {
        logger.debug("Finding all available itsnow hosts by types: {}", types);
        List<ItsnowHost> hosts = repository.findAllAvailableByTypes(types);
        logger.debug("Found size of all available itsnow hosts is: {}", hosts.size());
        return hosts;
    }

    @Override
    public void delete(ItsnowHost host) throws ItsnowHostException {
        logger.warn("Deleting {}", host);
        if (host.getProcessesCount() + host.getSchemasCount() != 0)
            throw new ItsnowHostException("Can't delete host for being used");

        SystemInvocation delistJob = translator.delist(host);
        delistJob.setId(DELIST_HOST + host.getAddress());
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
        if(jobId.equals(host.getProperty(CREATE_INVOCATION_ID))){
            return host.getStatus() == HostStatus.Running ? -1 : invokeService.read(jobId, offset, result);
        }else if (jobId.equals(host.getProperty(DELETE_INVOCATION_ID))){
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
        ItsnowHost host = hosts[hosts.length - 1];
        if( host.getLeftCapacity() <= 0 )
            throw new ItsnowHostException("There is no host available");
        return host;
    }

    //////////////////////////////////////////////////////////////////////////////
    // 监听 主机配置任务的执行情况，并通过repository更新相应的host状态
    //////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean care(SystemInvocation invocation) {
        String id = invocation.getId();
        return id.startsWith(TRUST_HOST_PREFIX) || id.startsWith(PROVISION_HOST) || id.startsWith(DELIST_HOST);
    }

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
