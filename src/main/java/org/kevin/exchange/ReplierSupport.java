/**
 *
 */
package org.kevin.exchange;

import java.util.concurrent.CompletableFuture;

public class ReplierSupport<T> implements Replier<T> {

    private Replier.Decoder<T> decoder;

    public ReplierSupport(Replier.Decoder<T> decoder) {
        this.decoder = decoder;
    }

    @Override
    public void reply(String resp, CompletableFuture<T> promise) {
        T result = decoder.decode(resp);
        promise.complete(result);
    }

}
