package com.hydt.app.config;

import com.hydt.app.config.dataSource.DataSourceAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by bean_huang on 2017/7/11.
 */
@Aspect
@Service
public class ControllerAspect {
    private static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Pointcut("execution(* com.hydt.app.controller.*.*(..))")
    public void controllerLog() {
    }

    @Before("controllerLog()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.error( "进入doBefore切面" + request.getRequestURI());
        logger.error(request.getHeader("User-Agent"));
        //打印所有头信息
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()){
            String header = headers.nextElement();
            logger.error("------------------"+ header+"--------------------");
            logger.error(request.getHeader(header));
        }
    }
}
