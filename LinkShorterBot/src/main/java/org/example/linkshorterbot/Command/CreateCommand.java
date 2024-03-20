package org.example.linkshorterbot.Command;

import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.state.State;
import org.example.linkshorterbot.util.TelegramUtil;
import org.springframework.stereotype.Component;

@Component("/create")
public class CreateCommand implements Command {
    @Override
    public State handleCommand(long chatId) {
        TelegramUtil.executeSendMessage(chatId, Constants.CREATE_TEXT);
        return State.AWAITING_URL;
    }

    @Override
    public String getDescription() {
        return Constants.CREATE_DESCRIPTION;
    }

}
