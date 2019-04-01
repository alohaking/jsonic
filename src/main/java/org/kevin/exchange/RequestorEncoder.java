package org.kevin.exchange;

import io.netty.buffer.ByteBufAllocator;

import java.util.List;

public class RequestorEncoder extends RequestorSupport {

    private final Requestor.Encoder encoder;

    /**
     * @param encoder
     */
    public RequestorEncoder(Requestor.Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    protected List<Object> writeRequests(ByteBufAllocator alloc) {
        return encoder.encode(alloc);
    }

    @Override
    public String toString() {
        return "RequestorEncoder{" +
                "encoder=" + encoder +
                '}';
    }
}
