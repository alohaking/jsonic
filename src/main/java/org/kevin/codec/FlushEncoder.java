package org.kevin.codec;

import org.kevin.enums.Command;

public class FlushEncoder extends CommandEncoder {
    private String uid;
    private Command command;
    public FlushEncoder(String collection, String bucket, String uid, Command command){
        super(collection, bucket);
        this.uid = uid;
        this.command = command;
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
        return this.command.name();
    }
}
