package com.hydt.app;

import com.hydt.app.service.CService;
import com.hydt.app.service.MyService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.awt.*;
import java.beans.ConstructorProperties;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by bean_huang on 2017/7/11.
 */
public class JavaTest {
    private static Logger logger = LoggerFactory.getLogger(JavaTest.class);

    @Test
    public void testCalssInterfaces(){
        Class cls = MyService.class;
        Class[] clss = cls.getInterfaces();
        for(Class c : clss){
            logger.debug(c.getName());
        }
    }

    @Test
    public void testInterfaces(){
        Class cls = CService.class;
        Class[] clss = cls.getInterfaces();
        for(Class c : clss){
            logger.debug(c.getName());
        }
    }

    @Test
    public void testDate(){
        Date d = new Date(-1L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug(sdf.format(d));
    }

    @Test
    public void testJava8Date(){
        Optional<Integer> optional = Optional.of(Integer.valueOf("a"));
        if(optional.isPresent()){
            logger.error("is null");
        } else {
            logger.error("is not null");
            Integer i = optional.orElse(1);
        }

    }

    @Test
    public void testMax(){
        Integer i1 = Integer.MAX_VALUE;
        Integer i2 = i1+1;
        System.out.println(i2>i1);
    }

    @Test
    public void testP(){
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.getBean("");
    }

    @Test
    public void testAWT(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // NullPointerException does not have to be thrown
        ((sun.awt.Win32GraphicsEnvironment) ge).displayChanged();
    }

    @Test
    public void testMove(){
        int i = 1;
        System.out.println(i<<2);
    }
}
