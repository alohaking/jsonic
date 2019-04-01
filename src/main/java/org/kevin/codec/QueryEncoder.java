package org.kevin.codec;

import org.kevin.enums.Command;

public class QueryEncoder extends CommandEncoder {
    private final String term;
    private int limit;
    private int offset;

    public QueryEncoder(String collection, String bucket, String term, int limit, int offset){
        super(collection, bucket);
        this.term = term;
        this.limit = limit;
        this.offset = offset;
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
        sb.append(escape(term.trim()));
        sb.append(" ");
        //limit
        if(limit > -1){
            sb.append("LIMIT(").append(limit).append(")").append(" ");
        }
        //offset
        if(offset > -1){
            sb.append("OFFSET(").append(offset).append(")");
        }
    }

    @Override
    public String getCommand() {
        return Command.QUERY.name();
    }
}
