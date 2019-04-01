/**
 *
 */
package org.kevin.exchange;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;

import java.util.List;

/**
 * 发送器
 *
 */
public interface Requestor {

    /**
     * @param channel
     */
    void request(Channel channel);

    /**
     *
     */
    interface Encoder {

        /**
         * @param alloc
         */
        List<Object> encode(ByteBufAllocator alloc);
    }
}
