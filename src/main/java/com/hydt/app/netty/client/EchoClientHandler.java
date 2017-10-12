package com.hydt.app.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by bean_huang on 2017/9/26.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMessage;

    public EchoClientHandler(){
        String message = "hello,world";
       firstMessage = Unpooled.buffer(message.length());
         /*for (int i = 0; i < firstMessage.capacity(); i++) {
            firstMessage.writeByte((byte)i);
        }*/

        firstMessage.writeBytes(message.getBytes(Charset.forName("utf-8")));
    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMessage);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ByteBuf){
            ByteBuf content = (ByteBuf)msg;
            System.out.println("msg: " + content.toString(Charset.forName("utf-8")));
            /*byte[] b = new byte[content.readableBytes()];
            content.readBytes(b);
            System.out.println("channelRead(from server): " + new String(b, Charset.forName("utf-8")));
            content.resetReaderIndex();*/
        }
        ctx.write(msg);
        System.out.println(msg.toString());
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
