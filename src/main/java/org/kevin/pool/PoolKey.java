package org.kevin.pool;

import org.kevin.SonicChannel;

import java.net.InetSocketAddress;

public class PoolKey {
    private final InetSocketAddress addr;
    private final SonicChannel channel;

    public PoolKey(InetSocketAddress addr, SonicChannel channel){
        this.addr = addr;
        this.channel = channel;
    }

    public InetSocketAddress getAddr() {
        return addr;
    }

    public SonicChannel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "PoolKey{" +
                "addr=" + addr +
                "channel=" + channel +
                '}';
    }
}
