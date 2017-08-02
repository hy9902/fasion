package com.hydt.app.config;

import com.hydt.app.amqp.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bean_huang on 2017/7/6.
 */
@Configuration
@EnableScheduling
public class AmqpConfig {
    private static Logger logger = LoggerFactory.getLogger(AmqpConfig.class);

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Sender sender(){
        return new Sender();
    }

    @Bean
    public Queue hydtQueue(){
        Queue queue = new Queue("hydt");
        return queue;
    }

    @Bean
    public Queue userQueue(){
        Queue queue = new Queue("userQueue",false);
        return queue;
    }


    @Bean
    public Queue dlQueue(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-message-ttl",1000*60);
        map.put("x-dead-letter-exchange","dlx");
        map.put("x-dead-letter-routing-key", "helloQue");
        Queue queue = new Queue("dlQue",false,false,false,map);
        return queue;
    }

    @Bean
    public FanoutExchange dlx(){
        FanoutExchange dlx = new FanoutExchange("dlx");
        return dlx;
    }


    @RabbitListener(id="hydt", queues = "hydt")
    private void dohydt(Message message){
        logger.debug("dohydt: " + new String(message.getBody()));
    }

}
