package com.hydt.app.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Created by bean_huang on 2017/9/26.
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ByteBuf){
            ByteBuf content = (ByteBuf)msg;
            System.out.println(content.toString(Charset.forName("utf-8")));
            /*byte[] b = new byte[content.readableBytes()];
            content.readBytes(b);
            System.out.println("channelRead(from client): " + new String(b, Charset.forName("utf-8")));
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
