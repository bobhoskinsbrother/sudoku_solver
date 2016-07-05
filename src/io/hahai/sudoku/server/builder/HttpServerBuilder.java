package io.hahai.sudoku.server.builder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import java.util.*;

public final class HttpServerBuilder {

    private int port = 0;

    private LinkedList<ContextHandler> contextsList;

    public HttpServerBuilder() {
        contextsList = new LinkedList<>();
    }

    public static HttpServerBuilder builder() {
        return new HttpServerBuilder();
    }

    public HttpServerBuilder port(int port) {
        this.port = port;
        return this;
    }

    public Server build() {
        final Server server = makeServer();
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(contextsList.toArray(new ContextHandler[contextsList.size()]));
        server.setHandler(contexts);
        return server;
    }

    private Server makeServer() {
        return new Server(port);
    }

    public HttpServerBuilder addToContext(String contextPath, ServletsBuilder builder) {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath(contextPath);
        for (BuilderHolder builderHolder : builder.servlets) {
            final ServletHolder servletHolder = new ServletHolder(builderHolder.servlet);
            context.addServlet(servletHolder, builderHolder.url);
        }
        for (String url : builder.resources.keySet()) {
            context.addServlet(builder.resources.get(url), url);
        }
        contextsList.add(context);

        return this;
    }

    public static class ServletsBuilder {
        private final List<BuilderHolder> servlets;
        private final Map<String, ServletHolder> resources;

        public ServletsBuilder() {
            servlets = new ArrayList<>();
            resources = new HashMap<>();
        }

        public static ServletsBuilder sb() {
            return new ServletsBuilder();
        }

        public ServletsBuilder addServlet(String uri, HttpServlet servlet) {
            servlets.add(new BuilderHolder(uri, servlet));
            return this;
        }

        public ServletsBuilder addDefault(String url, String flatFilesRoot) {
            ServletHolder holder = new ServletHolder(new DefaultServlet());
            holder.setInitParameter("resourceBase", flatFilesRoot);
            resources.put(url, holder);
            return this;
        }


    }

    private static class BuilderHolder {
        private final String url;
        private final HttpServlet servlet;

        private BuilderHolder(String url, HttpServlet servlet) {
            this.url = url;
            this.servlet = servlet;
        }
    }
}
