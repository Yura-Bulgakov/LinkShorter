package org.example.linkshorterbot.state;

import org.example.linkshorterbot.util.TelegramUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiFunction;

public class AwaitingNameStateHandler implements BiFunction<Long, Update, State> {
    @Override
    public State apply(Long chatId, Update update) {
        if (update.getMessage().hasText()) {
            TelegramUtil.executeSendMessage(chatId, "Здравствуйте, " + update.getMessage().getText() +
                    ".\nЕсли желаете создать короткую ссылку, введите команду /create");
            return State.NONE;
        }
        return State.AWAITING_NAME;
    }
}
