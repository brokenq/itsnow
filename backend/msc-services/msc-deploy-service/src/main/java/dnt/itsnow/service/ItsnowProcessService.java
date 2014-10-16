/**
 * Developer: Kadvin Date: 14-9-15 上午10:45
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.ItsnowProcessException;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;

import java.util.List;

/**
 * <h1>Itsnow Process Service</h1>
 */
public interface ItsnowProcessService {
    /**
     * <h2>根据关键词查找服务进程</h2>
     *
     * @param keyword  服务进程的关键词，可以为null
     * @param request  分页设置
     * @return 查找的结果
     */
    Page<ItsnowProcess> findAll(String keyword, PageRequest request);

    /**
     * <h2>根据进程标识查找服务进程</h2>
     * @param name 进程标识
     * @return 服务进程对象，查不到则返回null
     */
    ItsnowProcess findByName(String name);

    /**
     * <h2>创建服务进程</h2>
     *
     * @param creating  待创建的服务进程
     * @return 服务进程对象
     * @throws ItsnowProcessException
     */
    ItsnowProcess create(ItsnowProcess creating) throws ItsnowProcessException;

    /**
     * <h2>启动特定的服务进程</h2>
     * <pre>
     * 设计考虑：
     *   实现时，需要将启动服务进程的任务放到一个任务队列中，并返回相应任务的handle
     *   并由专门的执行器执行，执行过程中，外部可以根据前面的handle获取相应任务的详细信息
     *   也可以使用该任务的handle，取消执行
     * </pre>
     *
     * @param process 需要被启动的服务进程
     * @return 任务的job描述符
     * @throws ItsnowProcessException 启动异常
     */
    String start(ItsnowProcess process) throws ItsnowProcessException;


    /**
     * <h2>停止特定的服务进程</h2>
     * 与启动类似的设计
     *
     * @param process 需要被停止的服务进程
     * @return 任务的job描述符
     * @throws ItsnowProcessException 停止异常
     */
    String stop(ItsnowProcess process) throws ItsnowProcessException;

    /**
     * <h2>停止特定的进程相关任务</h2>
     *
     * @param process 被停止的服务进程
     * @param job     任务的job描述符
     * @throws ItsnowProcessException
     */
    void cancel(ItsnowProcess process, String job) throws ItsnowProcessException;

    /**
     * <h2>删除一个进程对象</h2>
     *
     *
     * @param process 被删除的进程
     * @throws ItsnowProcessException
     */
    void delete(ItsnowProcess process) throws ItsnowProcessException;

    /**
     * <h2>更新服务进程</h2>
     *
     * 当前主要是更新服务进程的状态
     *
     * @param process  被更新的主机
     * @throws ItsnowProcessException
     */
    void update(ItsnowProcess process)throws ItsnowProcessException;

    /**
     * <h2>读取一个服务进程相关任务的最新信息</h2>
     *
     * @param process 被读取的进程对象
     * @param job     正在/已经 执行的任务描述符
     * @param offset  信息的offset
     * @return 当前的位置，也是下次来读的始点
     */
    long follow(ItsnowProcess process, String job, long offset, List<String> result);
}
