/**
 * Developer: Kadvin Date: 14-9-15 上午10:45
 */
package dnt.itsnow.service;

import dnt.itsnow.exception.ItsnowProcessException;
import dnt.itsnow.model.ItsnowProcess;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.util.PageRequest;

/**
 * <h1>Itsnow Process Service</h1>
 */
public interface ItsnowProcessService {
    Page<ItsnowProcess> findAll(String keyword, PageRequest request);

    ItsnowProcess findByName(String name);

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
     * 停止特定的进程相关任务
     *
     * @param process 被停止的服务进程
     * @param job     任务的job描述符
     * @throws ItsnowProcessException
     */
    void cancel(ItsnowProcess process, String job) throws ItsnowProcessException;

    /**
     * 删除一个进程对象
     *
     *
     * @param process 被删除的进程
     * @throws ItsnowProcessException
     */
    void delete(ItsnowProcess process) throws ItsnowProcessException;

    /**
     * 读取一个服务进程相关任务的最新信息
     *
     * @param process 被读取的进程对象
     * @param job     正在/已经 执行的任务描述符
     * @param offset  信息的offset
     * @return 读取到的信息
     */
    String[] follow(ItsnowProcess process, String job, int offset);
}
