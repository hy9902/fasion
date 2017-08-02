package com.hydt.app.config;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.http.WebConnection;

/**
 * Created by bean_huang on 2017/7/18.
 */
@Configuration
public class SpringConfig {

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory(){
        JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory = new JettyEmbeddedServletContainerFactory();
        jettyEmbeddedServletContainerFactory.addServerCustomizers(new JettyServerCustomizer() {
            @Override
            public void customize(Server server) {
                HttpConfiguration http = new HttpConfiguration();
                http.setSecurePort(443);
                http.setSecureScheme("https");
                ServerConnector connector = new ServerConnector(server);
                connector.setPort(80);
                connector.addConnectionFactory(new HttpConnectionFactory(http));
                server.addConnector(connector);
            }
        });
        jettyEmbeddedServletContainerFactory.addConfigurations(new Http2HttpsConfig());
        return jettyEmbeddedServletContainerFactory;
    }

    private class Http2HttpsConfig extends AbstractConfiguration{

        @Override
        public void configure(WebAppContext context) throws Exception {
            super.configure(context);
            Constraint constraint = new Constraint();
            constraint.setDataConstraint(Constraint.DC_CONFIDENTIAL);

            ConstraintMapping constraintMapping = new ConstraintMapping();
            constraintMapping.setPathSpec("/*");
            constraintMapping.setConstraint(constraint);

            ConstraintSecurityHandler constraintSecurityHandler = new ConstraintSecurityHandler();
            constraintSecurityHandler.addConstraintMapping(constraintMapping);

            context.setSecurityHandler(constraintSecurityHandler);
        }
    }

}
