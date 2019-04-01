package org.kevin.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import org.kevin.SonicChannel;
import org.kevin.handler.AuthHandler;
import org.kevin.handler.SonicHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public final class SonicPool implements ChannelPool {

    private static final Logger LOG = LoggerFactory.getLogger(SonicPool.class);
    private final ChannelPool channelPool;

    public SonicPool(Bootstrap bootstrap,
              long readTimeout,
              long idleTimeout,
              int maxConnPerHost,
              String password,
              SonicChannel sonicChannel) {
        this.channelPool = new FixedChannelPool(
                bootstrap,
                new SonicPoolHandler(readTimeout, idleTimeout, sonicChannel, password),
                maxConnPerHost
        );
    }

    public Future<Channel> acquire() {
        return channelPool.acquire();
    }

    public Future<Void> release(Channel channel, Promise<Void> promise) {
        return channelPool.release(channel, promise);
    }

    public Future<Channel> acquire(Promise<Channel> promise) {
        return channelPool.acquire(promise);
    }

    public void close() {
        channelPool.close();
    }

    public Future<Void> release(Channel channel) {
        return channelPool.release(channel);
    }

    private static class SonicPoolHandler implements ChannelPoolHandler {
        final long readTimeout;
        final long idleTimeout; // 最大闲置时间(秒)
        final SonicChannel channel;
        final String password;

        SonicPoolHandler(long readTimeout, long idleTimeout, SonicChannel channel, String password) {
            this.readTimeout = readTimeout;
            this.idleTimeout = idleTimeout;
            this.channel = channel;
            this.password = password;
        }

        public void channelReleased(Channel channel) throws Exception {
            if (LOG.isDebugEnabled()) {
                LOG.debug("channel released : {}", channel.toString());
            }

            channel.pipeline().get(SonicHandler.class).operation(null);
        }

        public void channelAcquired(Channel channel) throws Exception {
            if (LOG.isDebugEnabled()) {
                LOG.debug("channel acquired : {}", channel.toString());
            }

            channel.pipeline().get(SonicHandler.class).operation(null);
        }

        public void channelCreated(Channel channel) throws Exception {
            if (LOG.isInfoEnabled()) {
                LOG.info("channel created : {}", channel.toString());
            }

            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast(new IdleStateHandler(readTimeout, 0, idleTimeout, TimeUnit.MILLISECONDS));
            pipeline.addLast(new LineBasedFrameDecoder(1024))
                    .addLast(new StringDecoder())
                    .addLast(new AuthHandler(password, this.channel))
                    .addLast(new SonicHandler());
        }
    }
}
