package org.kevin.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.kevin.enums.Command;
import org.kevin.SonicChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AuthHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthHandler.class);
    private final String password;
    private final SonicChannel channel;
    private AtomicBoolean isAuth = new AtomicBoolean(false);
    public AuthHandler(String password, SonicChannel channel){
        this.password = password;
        this.channel = channel;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String resp = (String) msg;
        if(resp.startsWith("CONNECTED")){
            if(!isAuth.get()){
                //auth with password
                StringBuffer sb = new StringBuffer();
                //command
                sb.append(Command.START.name());
                sb.append(" ");
                //channel
                sb.append(this.channel.name().toLowerCase());
                sb.append(" ");
                //password
                sb.append(this.password.trim());
                sb.append("\r\n");
                ByteBuf buf = ctx.alloc().buffer(sb.length());
                try {
                    buf.writeBytes(sb.toString().getBytes("utf-8"));
                } catch (UnsupportedEncodingException e) {
                    //ignore
                }
                ctx.writeAndFlush(buf);
            }

        }else if(resp.startsWith("STARTED")){
            isAuth.compareAndSet(false, true);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //ctx.pipeline().remove(this);
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if(!isAuth.get())
            super.channelInactive(ctx);
    }
}
