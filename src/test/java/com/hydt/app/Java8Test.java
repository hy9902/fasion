package com.hydt.app;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by bean_huang on 2017/7/13.
 */
public class Java8Test {
    private static Logger logger = LoggerFactory.getLogger(Java8Test.class);

    /**
     * Lambda 表达式,函数式接口
     * Java 8 提供了一个特殊的注解@FunctionalInterface
     * ex: Consumer,Runnable
     * Lambda 表达式的组成:
     * 1.A comma-separated list of formal parameters enclosed in parentheses
     * 2.The arrow token, ->
     * 3.A body, which consists of a single expression or a statement block.
     */
    @Test
    public void testLambda(){
        Arrays.asList(1,2,3).forEach(integer -> logger.debug(""+ Math.pow(integer,2)));
    }

    /**
     * 接口默认方法（不影响已实现的类），默认方法
     * 方法引用（::）
     * 1.类静态方法引用
     * 2.对象方法应用
     * 3.构造方法应用::new
     */
    @Test
    public void testInterfaceDefault(){

    }
}
