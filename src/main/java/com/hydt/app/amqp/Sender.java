package com.hydt.app.amqp;

import com.hydt.app.common.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by bean_huang on 2017/7/4.
 */
public class Sender {
    private static Logger logger = LoggerFactory.getLogger(Sender.class);
    private static int id = 1;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //@Scheduled(fixedDelay = 5000L)
    public void send() {
        logger.debug("amqp send");
        Calendar c = Calendar.getInstance();
        c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MessageProperties properties = new MessageProperties();
        Message message = new Message(sdf.format(c.getTime()).getBytes(),properties);
        this.rabbitTemplate.convertAndSend("hydt",sdf.format(c.getTime()));
        //this.rabbitTemplate.convertAndSend("hydt", new Date());
    }

    //@Scheduled(fixedDelay = 1000L)
    public void sendDeadLetter() {
        int delay = (int)(Math.random()*100000);
        logger.debug("amqp sendDeadLetter:" + delay);
        Calendar c = Calendar.getInstance();
        c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MessageProperties properties = new MessageProperties();
        properties.setExpiration("3000");
        properties.setDelay(delay);
        //properties.setExpiration();
        Message message = new Message( String.format("%d--%s",delay,sdf.format(c.getTime())).getBytes(), properties );
        this.rabbitTemplate.send("dlQue",message);
        //this.rabbitTemplate.convertAndSend("hydt", new Date());
    }

    @Scheduled(fixedDelay = 5000L)
    public void sendUser() {

        User user = new User();
        user.setName("ann");
        user.setAge((int)(Math.random()*100));
        user.setId(id++);
        logger.debug("amqp sendUser:"+ user.toString());
        this.rabbitTemplate.convertAndSend("userQueue",user);
        //this.rabbitTemplate.convertAndSend("hydt", new Date());
    }
}
