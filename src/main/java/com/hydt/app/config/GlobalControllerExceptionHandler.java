package com.hydt.app.config;

import com.hydt.app.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bean_huang on 2017/7/17.
 */
@ControllerAdvice

public class GlobalControllerExceptionHandler implements Thread.UncaughtExceptionHandler{

    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * <p>Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param t the thread
     * @param e the exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t.getName()+"---------------" + e.getLocalizedMessage());
    }
}
