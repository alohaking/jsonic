package org.kevin.codec;

import org.kevin.exchange.Replier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventQueryDecoder implements Replier.Decoder<List<String>> {
    @Override
    public List<String> decode(String buf) {
        return Optional.ofNullable(buf)
                .map(e -> Arrays.asList(e.split(" ")).stream().skip(3).collect(Collectors.toList()))
                .orElse(Collections.EMPTY_LIST);

    }
}
