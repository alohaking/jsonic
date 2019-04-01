package org.kevin.codec;

import org.kevin.enums.Command;

public class SuggestEncoder extends CommandEncoder {
    private final String word;
    private int limit;

    public SuggestEncoder(String collection, String bucket, String word, int limit){
        super(collection, bucket);
        this.word = word;
        this.limit = limit;
    }

    @Override
    public void wrapperCommand(StringBuffer sb) {
        //command
        sb.append(this.getCommand());
        sb.append(" ");
        //query word
        sb.append(this.getCollection().trim());
        sb.append(" ");
        sb.append(this.getBucket().trim());
        sb.append(" ");
        sb.append(escape(word.trim()));
        sb.append(" ");
        //limit
        if(limit > 0){
            sb.append("LIMIT(").append(limit).append(")");
        }
    }

    @Override
    public String getCommand() {
        return Command.SUGGEST.name();
    }
}
