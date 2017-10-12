package com.hydt.app.netty.client.telnet;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by bean_huang on 2017/10/10.
 */
@ChannelHandler.Sharable
public class TelnetClientHandler extends SimpleChannelInboundHandler<String> {

      @Override
      protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
          System.err.println(msg);
      }

      @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
          cause.printStackTrace();
          ctx.close();
      }
}
