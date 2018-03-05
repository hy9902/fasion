package com.hydt.app.config;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * Created by bean_huang on 2017/7/4.
 */
@Configuration
@ImportResource
public class JettyConfig {
    private final static Logger logger = LoggerFactory.getLogger(JettyConfig.class);

    @Value("${jetty.keyStore}")
    private String keyStore;

    @Value("${jetty.passWord}")
    private String passWord;

    @Value("${jetty.httpPort}")
    private int httpPort;

    @Value("${jetty.httpsPort}")
    private int httpsPort;

    @Bean
    public EmbeddedServletContainerCustomizer servletContainerCustomizer(){
        logger.error(String.format("http: %d ; https: %d", httpPort, httpsPort));

        return new EmbeddedServletContainerCustomizer(){
            /**
             * Customize the specified {@link ConfigurableEmbeddedServletContainer}.
             *
             * @param container the container to customize
             */
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof JettyEmbeddedServletContainerFactory) {
                    customizeJetty((JettyEmbeddedServletContainerFactory) container);
                    ((JettyEmbeddedServletContainerFactory) container).addConfigurations(new Http2HttpsConfig());
                }
            }


            private void customizeJetty(JettyEmbeddedServletContainerFactory factory) {
                factory.addServerCustomizers(new JettyServerCustomizer() {

                    @Override
                    public void customize(Server server) {
                        // HTTP
                        ServerConnector connector = new ServerConnector(server);
                        connector.setPort(httpPort);

                        // HTTPS
                        SslContextFactory sslContextFactory = new SslContextFactory();
                        try {
                            sslContextFactory.setKeyStorePath(ResourceUtils.getFile(keyStore).getPath());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        sslContextFactory.setKeyStorePassword(passWord);

                        HttpConfiguration https = new HttpConfiguration();
                        https.addCustomizer(new SecureRequestCustomizer());

                        ServerConnector sslConnector = new ServerConnector(
                                server,
                                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                                new HttpConnectionFactory(https));
                        sslConnector.setPort(httpsPort);
                        server.setConnectors(new Connector[] { connector, sslConnector });
                    }
                });

                //如果想设置http自动跳转https,则启用下面配置
                factory.addConfigurations(new Http2HttpsConfig());
            }
        };
    }

    private class Http2HttpsConfig extends AbstractConfiguration {

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
