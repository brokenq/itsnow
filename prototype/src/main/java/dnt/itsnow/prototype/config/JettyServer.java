/**
 * @author XiongJie, Date: 13-12-15
 */
package dnt.itsnow.prototype.config;

import dnt.spring.Bean;
import net.happyonroad.component.classworld.PomClassRealm;
import net.happyonroad.component.core.support.ComponentJarResource;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * The Jetty Server Instance
 */
public class JettyServer extends Bean {
    private static final String LOG_PATH = "logs/jetty_access.log";
    @Autowired
    private ApplicationContext applicationContext;
    @Value("${app.host}")
    private String             host;
    @Value("${http.port}")
    private Integer            port;
    // the jetty server
    private Server             server;

    public void performStart() {
        //don't bind at local ip, unless you specify 127.0.0.1
        if ("localhost".equalsIgnoreCase(host)) {
            host = "0.0.0.0";
        }
        server = new Server(new InetSocketAddress(host, port));
        server.setHandler(createHandlers());
        server.setStopAtShutdown(true);

        try {
            server.start();
            logger.info("Jetty bind at {}:{}", host, port);
        } catch (Exception e) {
            throw new RuntimeException("Can't start the jetty server", e);
        }
    }

    public void performStop() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException("Can't stop the jetty server", e);
        }
    }

    private HandlerCollection createHandlers() {
        WebAppContext context = new WebAppContext();
        context.setClassLoader(applicationContext.getClassLoader());
        context.setParentLoaderPriority(true);
        context.setBaseResource(Resource.newResource(new File("webapp")));

        //让Jetty能够找到主jar包里面的WebAppLoader
        PomClassRealm realm = (PomClassRealm) applicationContext.getClassLoader();
        ComponentJarResource jarResource = (ComponentJarResource) realm.getComponent().getResource();
        Resource resource = Resource.newResource(jarResource.getFile());
        context.getMetaData().addContainerResource(resource);

        AnnotationConfiguration configuration = new JettyAnnotationConfiguration();

        context.setConfigurations(new Configuration[]{configuration});

        List<Handler> handlers = new ArrayList<Handler>();
        handlers.add(context);

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(handlers.toArray(new Handler[handlers.size()]));

        RequestLogHandler log = new RequestLogHandler();
        log.setRequestLog(createRequestLog());

        HandlerCollection handlerCollection = new HandlerCollection();
        handlerCollection.setHandlers(new Handler[]{handlerList, log});

        return handlerCollection;
    }

    private RequestLog createRequestLog() {
        NCSARequestLog log = new NCSARequestLog();

        File logPath = new File(LOG_PATH);
        //noinspection ResultOfMethodCallIgnored
        logPath.getParentFile().mkdirs();

        log.setFilename(logPath.getPath());
        log.setRetainDays(30);
        log.setExtended(false);
        log.setAppend(true);
        log.setLogTimeZone("GMT");
        log.setLogLatency(true);
        return log;
    }

    private static class JettyAnnotationConfiguration extends AnnotationConfiguration {
        @Override
        public void configure(WebAppContext context) throws Exception {
            super.configure(context);
            //noinspection unchecked
            List<ContainerInitializer> is = (List<ContainerInitializer>) context.getAttribute(CONTAINER_INITIALIZERS);
            ContainerInitializer initializer = is.get(0);
            initializer.addApplicableTypeName(WebAppLoader.class.getName());
        }
    }
}
