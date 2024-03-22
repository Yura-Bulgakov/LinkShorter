package org.example.linkshorterbot.Command;

import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.state.State;
import org.example.linkshorterbot.util.TelegramUtil;
import org.springframework.stereotype.Component;

@Component("/stop")
public class StopCommand implements Command {

    @Override
    public State handleCommand(long chatId) {
        TelegramUtil.executeSendMessage(chatId, Constants.STOP_TEXT);
        return State.NONE;
    }

    @Override
    public String getDescription() {
        return Constants.STOP_DESCRIPTION;
    }
}
