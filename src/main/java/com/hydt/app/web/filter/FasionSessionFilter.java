package com.hydt.app.web.filter;

import com.hydt.app.web.BodyReaderHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by bean_huang on 2017/7/18.
 */
@WebFilter(urlPatterns = "/*", filterName = "fasionSessionFilter")
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
        logger.debug("sessionFilter_url:" + request.getRequestURL().toString());
        if(HttpMethod.POST.matches(request.getMethod().toUpperCase())){
            BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
            Enumeration<String> enumeration = requestWrapper.getParameterNames();
            while (enumeration.hasMoreElements()){
                String key = enumeration.nextElement();
                String value = request.getParameter(key);
                logger.error(String.format("key=%s, value=%s", key, value));
            }
        }

        filterChain.doFilter(request,response);
    }
}
