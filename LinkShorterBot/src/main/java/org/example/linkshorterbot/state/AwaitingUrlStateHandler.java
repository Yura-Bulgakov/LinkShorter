package org.example.linkshorterbot.state;

import org.example.linkshorterbot.model.RequestContainer;
import org.example.linkshorterbot.model.TokenCreationRequest;
import org.example.linkshorterbot.util.KeyboardFactory;
import org.example.linkshorterbot.util.TelegramUtil;
import org.example.linkshorterbot.util.UrlValidator;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiFunction;

public class AwaitingUrlStateHandler implements BiFunction<Long, Update, State> {
    @Override
    public State apply(Long chatId, Update update) {
        if (update.hasMessage() && update.getMessage().hasText() &&
                !UrlValidator.isValid(update.getMessage().getText())) {
            TelegramUtil.executeSendMessage(chatId, "Ссылка не валидная. Пожалуйста, введите валидную ссылку.");
            return State.AWAITING_URL;
        }
        TokenCreationRequest tokenCreationRequest = new TokenCreationRequest();
        tokenCreationRequest.setUrl(update.getMessage().getText());
        RequestContainer.container.put(chatId, tokenCreationRequest);
        TelegramUtil.promptWithKeyboard(chatId, "Выберите стратегию генерации токена",
                KeyboardFactory.getTokenSelectionKeyboard());
        return State.GENERATION_TOKEN_SELECTION;
    }
}
