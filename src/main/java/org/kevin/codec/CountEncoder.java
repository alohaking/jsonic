package org.kevin.codec;

import org.kevin.enums.Command;

public class CountEncoder extends CommandEncoder {
    private String uid;
    public CountEncoder(String collection, String bucket, String uid){
        super(collection, bucket);
        assert  collection != null;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public void wrapperCommand(StringBuffer sb) {
        //command
        sb.append(this.getCommand());
        sb.append(" ");
        //query term
        sb.append(this.getCollection().trim());
        if(this.getBucket() != null){
            sb.append(" ").append(this.getBucket());
        }
        if(this.getUid() != null){
            sb.append(" ").append(this.getUid());
        }
    }

    @Override
    public String getCommand() {
        return Command.COUNT.name();
    }
}
