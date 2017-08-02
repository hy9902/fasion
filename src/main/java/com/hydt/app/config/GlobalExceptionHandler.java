package com.hydt.app.config;

import com.hydt.app.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bean_huang on 2017/7/17.
 */
@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public Result hanlderCommonException(Throwable t){
        t.printStackTrace();
        return Result.error(-1,t.getLocalizedMessage(),null);
    }
}
