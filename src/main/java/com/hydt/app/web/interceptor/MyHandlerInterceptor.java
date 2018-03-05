package com.hydt.app.web.interceptor;

import com.hydt.app.config.JettyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bean_huang on 2017/7/18.
 */
public class MyHandlerInterceptor implements HandlerInterceptor{
    private final static Logger logger = LoggerFactory.getLogger(MyHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.error("preHandle :-----------------" + handler.getClass().getName() + "-----------------------");
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.error("postHandle :-----------------" + handler.getClass().getName() + "-----------------------");
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("afterCompletion :-----------------" + handler.getClass().getName() + "-----------------------");
    }
}
