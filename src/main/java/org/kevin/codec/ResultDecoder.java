package org.kevin.codec;

import org.kevin.exchange.Replier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResultDecoder implements Replier.Decoder<Long> {
    @Override
    public Long decode(String buf) {
        return Optional.ofNullable(buf)
                .map(e -> {
                    String[] arr = e.split(" ");
                    if(arr.length > 1){
                        return Long.parseLong(arr[1]);
                    }
                    return 0L;
                }).orElse(0L);

    }
}
