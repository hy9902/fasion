package com.hydt.app.amqp;

import com.hydt.app.common.User;
import com.hydt.app.config.AmqpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created by bean_huang on 2017/7/7.
 */
@Component
public class Reciever {
    private static Logger logger = LoggerFactory.getLogger(Reciever.class);

    //@RabbitHandler
    @RabbitListener(queues = "helloQue")
    @SendTo("hydt")
    private String doHello2(Message message){
        //new String(message.getBody())
        String msg = new String(message.getBody());
        logger.debug("doHello2: " + msg );
        return msg;
    }

    @RabbitListener(queues = "userQueue")
    @SendTo("dlQue")
    public String handlerUser(User user){
        logger.error(user.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
