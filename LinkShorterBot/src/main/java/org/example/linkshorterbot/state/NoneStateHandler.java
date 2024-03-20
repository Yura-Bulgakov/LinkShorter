package org.example.linkshorterbot.state;

import org.example.linkshorterbot.util.TelegramUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiFunction;

public class NoneStateHandler implements BiFunction<Long, Update, State> {
    @Override
    public State apply(Long chatId, Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            TelegramUtil.executeSendMessage(chatId, "Для взаимодействия введите команду");
        }
        return State.NONE;
    }
}
