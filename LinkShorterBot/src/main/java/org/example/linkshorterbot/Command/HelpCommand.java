package org.example.linkshorterbot.Command;

import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.state.State;
import org.example.linkshorterbot.util.TelegramUtil;
import org.springframework.stereotype.Component;

@Component("/help")
public class HelpCommand implements Command {
    @Override
    public State handleCommand(long chatId) {
        TelegramUtil.executeSendMessage(chatId, Constants.HELP_TEXT);
        return State.NONE;
    }

    @Override
    public String getDescription() {
        return Constants.HELP_DESCRIPTION;
    }

}
