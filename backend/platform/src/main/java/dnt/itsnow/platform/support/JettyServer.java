/**
 * Developer: Kadvin Date: 14-7-14 下午4:05
 */
package dnt.itsnow.platform.support;

import dnt.spring.Bean;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;

import javax.servlet.ServletContainerInitializer;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/** The Jetty Server Instance */
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
            throw new ApplicationContextException("Can't start the jetty server", e);
        }
    }

    public void performStop() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new ApplicationContextException("Can't stop the jetty server", e);
        }
    }

    private HandlerCollection createHandlers() {
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        //ServletHolder holder = context.addServlet(DefaultServlet.class, "/");
        //holder.setInitParameter("aliases", "true"); // support serve symbolic links

        File webapp = new File(System.getProperty("app.home"), "webapp");
        String path = FilenameUtils.normalize(webapp.getAbsolutePath());
        context.setBaseResource(Resource.newResource(new File(path)));
        context.setClassLoader(applicationContext.getClassLoader());
        context.getServletContext().setAttribute("application", applicationContext);
        context.setConfigurations(new Configuration[]{new JettyAnnotationConfiguration()});

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

    static class JettyAnnotationConfiguration extends AnnotationConfiguration {
        @Override
        public void createServletContainerInitializerAnnotationHandlers(WebAppContext context,
                                                                        List<ServletContainerInitializer> scis)
                throws Exception {
            super.createServletContainerInitializerAnnotationHandlers(context, scis);
            //noinspection unchecked
            List<ContainerInitializer> initializers =
                    (List<ContainerInitializer>) context.getAttribute(CONTAINER_INITIALIZERS);
            ContainerInitializer initializer = initializers.get(0);
            initializer.addApplicableTypeName(SpringMvcLoader.class.getName());

        }
    }
}