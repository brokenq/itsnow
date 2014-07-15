/**
 * Developer: Kadvin Date: 14-5-16 上午9:46
 */
package dnt.itsnow.platform.services;

import org.springframework.context.ApplicationEvent;

/**
 * 所有服务包的事件
 */
public class ServicePackageEvent extends ApplicationEvent {
    public ServicePackageEvent(Object source) {
        super(source);
    }

    /**
     * the service packages is loading
     */
    public static class LoadingEvent extends ServicePackageEvent {
        public LoadingEvent(Object source) {
            super(source);
        }
    }


    /**
     * the service packages are loaded and ready for further works
     */
    public static class LoadedEvent extends ServicePackageEvent {
        public LoadedEvent(Object source) {
            super(source);
        }
    }

    /**
     * the service packages is unloading
     */
    public static class UnloadingEvent extends ServicePackageEvent {
        public UnloadingEvent(Object source) {
            super(source);
        }
    }

    /**
     * the service packages is unloaded
     */
    public static class UnloadedEvent extends ServicePackageEvent {
        public UnloadedEvent(Object source) {
            super(source);
        }
    }
}
