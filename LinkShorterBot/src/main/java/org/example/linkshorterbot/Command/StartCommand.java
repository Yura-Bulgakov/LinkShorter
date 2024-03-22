package org.example.linkshorterbot.Command;

import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.state.State;
import org.example.linkshorterbot.util.TelegramUtil;
import org.springframework.stereotype.Component;

@Component("/start")
public class StartCommand implements Command {
    @Override
    public State handleCommand(long chatId) {
        TelegramUtil.executeSendMessage(chatId, Constants.START_TEXT);
        return State.AWAITING_NAME;
    }

    @Override
    public String getDescription() {
        return Constants.START_DESCRIPTION;
    }
}
