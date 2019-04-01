package org.kevin.codec;

import org.kevin.enums.Command;

public class PopEncoder extends PushEncoder {
    public PopEncoder(String collection, String bucket, String uid, String text){
        super(collection, bucket, uid, text);
    }
    @Override
    public String getCommand() {
        return Command.POP.name();
    }
}
