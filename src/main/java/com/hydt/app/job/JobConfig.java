package com.hydt.app.job;

import com.hydt.app.config.ScheduleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by bean_huang on 2017/9/25.
 */
@Configuration
@Async
public class JobConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ConcurrentHashMap<String, ScheduledFuture> map = new ConcurrentHashMap<>();

    @Autowired
    ScheduleConfig scheduleConfig;


    public void startJob(String key){
        ScheduledFuture scheduledFuture = ((ThreadPoolTaskScheduler)scheduleConfig.getAsyncExecutor()).schedule(new Runnable() {
            @Override
            public void run() {
                logger.error("error from startJob");
            }
        }, new CronTrigger("*/10 * * * * *"));
        map.put(key, scheduledFuture);
    }

    public void stopJob(String key){
        ScheduledFuture scheduledFuture = map.get(key);
        if(scheduledFuture.cancel(true)){
            logger.error("取消成功");
        } else {
            logger.error("取消失败");
        }
    }
}
