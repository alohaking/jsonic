package org.kevin.codec;

import org.kevin.enums.Command;

public class PingEncoder extends CommandEncoder {

    public PingEncoder(){
    }
    @Override
    public void wrapperCommand(StringBuffer sb) {
        //command
        sb.append(this.getCommand());
    }

    @Override
    public String getCommand() {
        return Command.PING.name();
    }
}
