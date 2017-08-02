package com.hydt.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by bean_huang on 2017/7/12.
 */
@Configuration
@ConfigurationProperties(prefix = "dc", ignoreInvalidFields = true)
//@PropertySource不支持yml的属性文件
//@PropertySource("classpath:my.properties")
public class TcScopeConfig {

    private boolean enable;

    private Map<String,String> eir;

    private List<String> pc;

    public List<String> getPc() {
        return pc;
    }

    public void setPc(List<String> pc) {
        this.pc = pc;
    }

    public Map<String, String> getEir() {
        return eir;
    }

    public void setEir(Map<String, String> eir) {
        this.eir = eir;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
