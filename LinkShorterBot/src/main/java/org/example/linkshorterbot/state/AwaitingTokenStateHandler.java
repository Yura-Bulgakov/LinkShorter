package org.example.linkshorterbot.state;

import org.example.linkshorterbot.model.RequestContainer;
import org.example.linkshorterbot.model.TokenCreationRequest;
import org.example.linkshorterbot.util.TelegramUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiFunction;

public class AwaitingTokenStateHandler implements BiFunction<Long, Update, State> {

    @Override
    public State apply(Long chatId, Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            TelegramUtil.executeSendMessage(chatId, "Пожалуйста, введите токен.");
            return State.AWAITING_TOKEN;
        }
        TokenCreationRequest request = RequestContainer.container.get(chatId);
        request.setToken(update.getMessage().getText());
        return TelegramUtil.postRequestForToken(chatId, request);
    }

}
