package org.kevin.codec;

import org.kevin.enums.Command;

public class HelpEncoder extends CommandEncoder {
    private String manual;

    public HelpEncoder(String manual){
        this.manual = manual;
    }
    @Override
    public void wrapperCommand(StringBuffer sb) {
        //command
        sb.append(this.getCommand());
        sb.append(" ");
        sb.append(this.manual);
    }

    @Override
    public String getCommand() {
        return Command.HELP.name();
    }
}
