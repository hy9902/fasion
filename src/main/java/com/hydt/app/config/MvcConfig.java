package com.hydt.app.config;

import com.hydt.app.web.interceptor.MyHandlerInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.UrlPathHelper;

/**
 * Created by bean_huang on 2017/7/18.
 *
 * extend {@link WebMvcConfigurationSupport} or {@link WebMvcConfigurerAdapter}
 *
 *
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 注册spring interceptor
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyHandlerInterceptor());
        super.addInterceptors(registry);
    }

    /**
     * 全局注册跨域请求过滤器；同时可以注入 {@link FilterRegistrationBean}
     * 对于单个类和方法，可以使用@CrossOrigin
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowedHeaders("header1", "header2", "header3")
                .exposedHeaders("header1", "header2")
                .allowCredentials(false).maxAge(3600);
        super.addCorsMappings(registry);
    }

    /**
     * FilterRegistrationBean 过滤器注册器
     * 同时可以继承 {@link CorsFilter},并通过@WebFilter注入
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:9000");
        config.addAllowedOrigin("null");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        // CORS 配置对所有接口都有效
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
