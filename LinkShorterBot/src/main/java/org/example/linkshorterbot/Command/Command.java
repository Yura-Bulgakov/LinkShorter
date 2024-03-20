package org.example.linkshorterbot.Command;

import org.example.linkshorterbot.state.State;

public interface Command {
    State handleCommand(long chatId);

    String getDescription();
}
