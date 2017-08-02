package com.hydt.app.config.dataSource;

import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * Created by bean_huang on 2017/7/11.
 */
@Aspect
@Service
public class DataSourceAspect {
    private static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(* com.hydt.app.service.*.*(..))")
    public void dataSourcePointcut() {
    }

    @Before("dataSourcePointcut()")
    public void doBefore(JoinPoint joinPoint){
        Class<?> target = joinPoint.getTarget().getClass();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解类
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());

    }
    /**
     * 提取目标对象方法注解和类注解中的数据源标识
     */
    private void resolveDataSource(Class<?> clazz, Method method){
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.setDataSource(source.value());
            }
            // 方法注解可以覆盖类注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.setDataSource(source.value());
            }
        } catch (Exception e) {
            System.out.println(clazz + ":" + e.getMessage());
        }
    }

    @After("dataSourcePointcut()")
    public void doAfter(JoinPoint joinPoint){
        DynamicDataSourceHolder.clearDataSource();
    }


    @Before("@annotation(ds)")
    public void doAnnotationBefore(JoinPoint joinPoint, DataSource ds){

    }
}
