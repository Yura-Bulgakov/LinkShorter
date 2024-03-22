package org.example.linkshorterbot.state;

import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.model.RequestContainer;
import org.example.linkshorterbot.model.TokenCreationRequest;
import org.example.linkshorterbot.util.KeyboardFactory;
import org.example.linkshorterbot.util.TelegramUtil;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiFunction;

public class GenerationTokenSelectionStateHandler implements BiFunction<Long, Update, State> {

    @Override
    public State apply(Long chatId, Update update) {
        if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(Constants.AUTO_BUTTON_CALLBACK)) {
            return handleAutomaticGenerationSelection(chatId);
        } else if (update.hasCallbackQuery() &&
                update.getCallbackQuery().getData().equals(Constants.MANUAL_BUTTON_CALLBACK)) {
            TelegramUtil.executeSendMessage(chatId, "Введите короткий токен.");
            return State.AWAITING_TOKEN;
        } else {
            TelegramUtil.promptWithKeyboard(chatId, "Пожалуйста, выберите стратегию генерации токена.",
                    KeyboardFactory.getTokenSelectionKeyboard());
            return State.GENERATION_TOKEN_SELECTION;
        }
    }

    private State handleAutomaticGenerationSelection(long chatId) {
        TokenCreationRequest request = RequestContainer.container.get(chatId);
        request.setToken(null);
        return TelegramUtil.postRequestForToken(chatId, request);
    }

}
