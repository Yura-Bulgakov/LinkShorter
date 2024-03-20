package org.example.linkshorterbot.bot;

import org.example.linkshorterbot.Command.Command;
import org.example.linkshorterbot.state.State;
import org.example.linkshorterbot.util.TelegramUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;


public class ResponseHandler {
    private final Map<Long, State> chatStates = new HashMap<>();

    private final Map<String, Command> mapCommand;

    public ResponseHandler(Map<String, Command> mapCommand) {
        this.mapCommand = mapCommand;
    }

    public void replyHandling(long chatId, Update update) {
        if (update.hasMessage() && update.getMessage().getText().startsWith("/")) {
            handleCommand(chatId, update);
        } else {
            handleState(chatId, update);
        }
    }

    private void handleCommand(long chatId, Update update) {
        if (mapCommand.containsKey(update.getMessage().getText())) {
            chatStates.put(chatId,
                    mapCommand.get(update.getMessage().getText()).handleCommand(chatId));
        } else {
            TelegramUtil.executeSendMessage(chatId, "Операция не поддерживается.");
        }
    }

    private void handleState(long chatId, Update update) {
        State state = chatStates.get(chatId);
        State newState = chatStates.get(chatId).handle(chatId, update);
        if (!state.equals(newState)) {
            chatStates.put(chatId, newState);
        }
    }

    public Map<Long, State> getChatStates() {
        return chatStates;
    }
}
