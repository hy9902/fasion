package com.hydt.app.config;

import com.hydt.app.common.Result;
import org.springframework.security.web.util.TextEscapeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

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

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        /*webDataBinder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            *//**
             * Sets the property value by parsing a given String.  May raise
             * java.lang.IllegalArgumentException if either the String is
             * badly formatted or if this kind of property can't be expressed
             * as text.
             *
             * @param text The string to be parsed.
             *//*
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(text == null ? null : TextEscapeUtils.escapeEntities(text.trim()));
            }
        });*/
    }
}
