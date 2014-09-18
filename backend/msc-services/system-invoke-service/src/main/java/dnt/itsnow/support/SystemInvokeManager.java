/**
 * Developer: Kadvin Date: 14-9-15 下午4:15
 */
package dnt.itsnow.support;

import dnt.concurrent.OnceTrigger;
import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.listener.SystemInvocationListener;
import dnt.itsnow.model.SystemInvocation;
import dnt.itsnow.service.SystemInvokeService;
import dnt.spring.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * <h1>系统调用服务</h1>
 */
@Service
public class SystemInvokeManager extends Bean implements SystemInvokeService {
    private Set<SystemInvocationListener> listeners = new LinkedHashSet<SystemInvocationListener>();
    @Autowired
    @Qualifier("systemInvokeExecutor")
    ExecutorService systemInvokeExecutor;

    @Autowired
    @Qualifier("timeoutScheduler")
    protected TaskScheduler cleanScheduler;

    private Map<String, Future>             futures   = new ConcurrentHashMap<String, Future>();
    private Map<String, InvocationExecutor> executors = new ConcurrentHashMap<String, InvocationExecutor>();

    @Override
    public void addListener(SystemInvocationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(SystemInvocationListener listener) {
        listeners.remove(listener);
    }

    @Override
    public String addJob(final SystemInvocation invocation) {
        invocation.setId(UUID.randomUUID().toString());
        InvocationExecutor executor = createExecutor(invocation);
        executors.put(invocation.getId(), executor);
        Future<?> future = systemInvokeExecutor.submit(executor);
        futures.put(invocation.getId(), future);

        InvocationKiller killer = createKiller(executor, future);
        OnceTrigger trigger = new OnceTrigger(System.currentTimeMillis() + invocation.getTimeout());
        cleanScheduler.schedule(killer, trigger);

        broadcast(new ListenerNotifier() {
            public void notify(SystemInvocationListener listener) {
                listener.added(invocation);
            }
        });
        return invocation.getId();
    }

    @Override
    public void cancelJob(String invocationId) {
        Future future = futures.get(invocationId);
        if( future == null ) return;
        final InvocationExecutor executor = getExecutor(invocationId);
        if(executor == null) return;
        cancel(future, executor);
    }

    private InvocationExecutor getExecutor(String invocationId) {
        return executors.get(invocationId);
    }

    @Override
    public boolean isFinished(String invocationId) {
        Future future = futures.get(invocationId);
        return future == null || future.isDone();
    }

    @Override
    public void waitJobFinished(String invocationId) throws SystemInvokeException {
        Future future = futures.get(invocationId);
        if( future == null ) return;
        try {
            //TODO 是不是应该有个默认的最大等待时长?
            future.get();
        } catch (InterruptedException e) {
            throw new SystemInvokeException("Interrupted while wait", e);
        } catch (ExecutionException e) {
            throw new SystemInvokeException("Execution failed while wait", e);
        }
    }

    @Override
    public String[] read(String invocationId, int offset) {
        return new String[0];
    }

    ////////////////////////////////////////////////////////////////////
    // Inner Implementations
    ////////////////////////////////////////////////////////////////////


    InvocationExecutor createExecutor(SystemInvocation invocation) {
        return new InvocationExecutor(invocation);
    }

    InvocationKiller createKiller(InvocationExecutor executor, Future<?> future) {
        return new InvocationKiller(executor, future);
    }

    void broadcast(ListenerNotifier notifier){
        for (SystemInvocationListener listener : listeners) {
            try {
                notifier.notify(listener);
            } catch (Exception e) {
                logger.warn("Error while notify " + listener, e);
            }
        }
    }

    protected void cancel(Future future, final InvocationExecutor executor) {
        if( future.isDone() ) return;
        if( future.isCancelled() ) return;
        executor.cancel();
        if( future.cancel(true)){
            broadcast(new ListenerNotifier() {
                @Override
                public void notify(SystemInvocationListener listener) {
                    listener.cancelled(executor.invocation);
                }
            });
        }
    }


    class InvocationExecutor implements Runnable{
        private final SystemInvocation invocation;

        public InvocationExecutor(SystemInvocation invocation) {
            this.invocation = invocation;
        }

        @Override
        public void run() {
            broadcast(new ListenerNotifier() {
                @Override
                public void notify(SystemInvocationListener listener) {
                    listener.started(invocation);
                }
            });
            // perform real invocation

            broadcast(new ListenerNotifier() {
                @Override
                public void notify(SystemInvocationListener listener) {
                    listener.finished(invocation);
                }
            });
            clean();
        }

        public void cancel() {
            // TODO: TRY TO CANCEL this executing task
            // such as set the flag to break the while loop
            // or execute a kill -9 for the started process
        }

        protected void clean() {
            executors.remove(invocation.getId());
            futures.remove(invocation.getId());
        }
    }

    class InvocationKiller implements Runnable {
        private final InvocationExecutor executor;
        private final Future<?>          future;

        public InvocationKiller(InvocationExecutor executor, Future<?> future) {
            this.executor = executor;
            this.future = future;
        }

        @Override
        public void run() {
            cancel(future, executor);
            executor.clean();
        }
    }

    static interface ListenerNotifier {
        void notify(SystemInvocationListener listener);
    }
}
