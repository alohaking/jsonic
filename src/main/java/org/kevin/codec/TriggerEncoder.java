package org.kevin.codec;

import org.kevin.enums.Command;

public class TriggerEncoder extends CommandEncoder {
    private String action;

    public TriggerEncoder(String action){
        this.action = action;
    }
    @Override
    public void wrapperCommand(StringBuffer sb) {
        //command
        sb.append(this.getCommand());
        sb.append(" ");
        sb.append(this.action);
    }

    @Override
    public String getCommand() {
        return Command.TRIGGER.name();
    }
}
