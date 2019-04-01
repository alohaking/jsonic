package org.kevin.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.apache.commons.lang3.StringUtils;
import org.kevin.SonicChannel;
import org.kevin.exchange.RequestorEncoder;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

public abstract class CommandEncoder implements RequestorEncoder.Encoder {
    private String collection;
    private String bucket;
    private SonicChannel sonicChannel;
    private String password;
    public CommandEncoder(String collection,
                          String bucket){
        this.collection = collection;
        this.bucket = bucket;
    }
    public CommandEncoder(SonicChannel sonicChannel,
                          String password){
        this.sonicChannel = sonicChannel;
        this.password = password;
    }
    public CommandEncoder(){

    }

    public String getCollection() {
        return collection;
    }


    public String getBucket() {
        return bucket;
    }

    public SonicChannel getSonicChannel() {
        return sonicChannel;
    }

    public String getPassword() {
        return password;
    }

    /**
     * get command
     * @return
     */
    public abstract String getCommand();

    public List<Object> encode(ByteBufAllocator alloc) {
        StringBuffer sb = new StringBuffer();
        this.wrapperCommand(sb);
        sb.append("\r\n");
        ByteBuf buf = alloc.buffer(sb.length());
        try {
            buf.writeBytes(sb.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            //ignore
        }
        return Collections.singletonList(buf);
    }

    protected String escape(String t){
        return '"' + t.replaceAll("\"", "\\\"").replaceAll("\r\n", " ") + '"';
    }
    public abstract void wrapperCommand(StringBuffer sb);
}
