package com.hydt.app.web.filter;

import com.hydt.app.web.BodyReaderHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

/**
 * Created by bean_huang on 2017/7/18.
 * servlet 3.0 注解声明的 @WebFilter @WebListener 等在javax.servlet.annotation包的，需要在配置
 * @ServletComponentScan 扫描,才能启用
 *
 * 通过spring bean 配置 FilterRegistrationBean、ServletRegistrationBean、ServletListenerRegistrationBean
 * 分别对应配置原生的Filter、Servlet、Listener
 */
@WebFilter(urlPatterns = "/*", filterName = "fasionSessionFilter")
@Order(99)
public class FasionSessionFilter extends OncePerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(FasionSessionFilter.class);

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("sessionFilter_url:" + request.getClass().getName());
        if(HttpMethod.POST.matches(request.getMethod().toUpperCase())){

            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()){
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                logger.error(String.format("原始请求Header key=%s, value=%s", key, value));
            }



            BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
            enumeration = requestWrapper.getParameterNames();
            while (enumeration.hasMoreElements()){
                String key = enumeration.nextElement();
                String value = request.getParameter(key);
                logger.error(String.format("Parameter key=%s, value=%s", key, value));
            }

            enumeration = requestWrapper.getHeaderNames();
            while (enumeration.hasMoreElements()){
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                logger.error(String.format("Header key=%s, value=%s", key, value));
            }
        }

        filterChain.doFilter(request,response);
    }
}
