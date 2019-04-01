package org.kevin.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SonicPoolGroup extends AbstractChannelPoolMap<PoolKey, SonicPool> {

    private static final Logger LOG = LoggerFactory.getLogger(SonicPoolGroup.class);

    private final EventLoopGroup loopGroup;

    private final long connectTimeout;
    private final long readTimeout;
    private final long idleTimeout;
    private final int maxConnPerHost;
    private final String password;

    public SonicPoolGroup(EventLoopGroup loopGroup,
                     long connectTimeout,
                     long readTimeout,
                     long idleTimeout,
                     int maxConnPerHost,
                     String password) {
        this.loopGroup = loopGroup;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.idleTimeout = idleTimeout;
        this.maxConnPerHost = maxConnPerHost;
        this.password = password;
    }

    @Override
    protected SonicPool newPool(PoolKey poolKey) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("channel pool created : {}", poolKey.getAddr());
        }
        Bootstrap bootstrap = null;
        if(Epoll.isAvailable()){

        }else{
            bootstrap = new Bootstrap().channel(NioSocketChannel.class).group(loopGroup);
            bootstrap.remoteAddress(poolKey.getAddr());
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) connectTimeout);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        }

        return new SonicPool(
                bootstrap,
                readTimeout,
                idleTimeout,
                maxConnPerHost,
                password,
                poolKey.getChannel()
        );
    }
}