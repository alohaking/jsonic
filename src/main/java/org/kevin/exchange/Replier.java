package org.kevin.exchange;

import io.netty.buffer.ByteBuf;

import java.util.concurrent.CompletableFuture;


public interface Replier<T> {

    /**
     * @param in
     * @param promise
     */
    void reply(String in, CompletableFuture<T> promise);

    /**
     * 响应解码器
     *
     */
    interface Decoder<T> {

        /**
         * 解码响应
         *
         * @param resp
         * @return
         */
        T decode(String resp);
    }

    /**
     * 空响应解码器
     *
     */
    enum NOPDecoder implements Decoder<String> {

        INSTANCE;
        @Override
        public String decode(String buf) {
            return buf;
        }

    }
}
