package org.kevin.codec;

import org.kevin.enums.Command;

public class PushEncoder extends CommandEncoder {
    private String uid;
    private String text;

    public PushEncoder(String collection, String bucket, String uid, String text){
        super(collection, bucket);
        this.uid = uid;
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public String getText() {
        return text;
    }

    @Override
    public void wrapperCommand(StringBuffer sb) {
        //command
        sb.append(this.getCommand());
        sb.append(" ");
        //query term
        sb.append(this.getCollection().trim());
        sb.append(" ");
        sb.append(this.getBucket().trim());
        sb.append(" ");
        sb.append(this.getUid());
        sb.append(" ");
        sb.append(escape(this.getText()));
    }

    @Override
    public String getCommand() {
        return Command.PUSH.name();
    }
}
