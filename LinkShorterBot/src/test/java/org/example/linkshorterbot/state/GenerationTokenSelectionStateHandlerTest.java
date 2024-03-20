package org.example.linkshorterbot.state;

import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.model.RequestContainer;
import org.example.linkshorterbot.model.TokenCreationRequest;
import org.example.linkshorterbot.util.KeyboardFactory;
import org.example.linkshorterbot.util.TelegramUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerationTokenSelectionStateHandlerTest {

    @Test
    void testApplyWithAutoButtonCallback() {
        Update update = new Update();
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData(Constants.AUTO_BUTTON_CALLBACK);
        update.setCallbackQuery(callbackQuery);
        TokenCreationRequest request = new TokenCreationRequest();
        try (MockedStatic<TelegramUtil> telegramUtilMock = Mockito.mockStatic(TelegramUtil.class)) {
            GenerationTokenSelectionStateHandler handler = new GenerationTokenSelectionStateHandler();
            long chatId = 1L;
            RequestContainer.container.put(chatId, request);
            when(TelegramUtil.postRequestForToken(chatId, request)).thenReturn(State.NONE);

            State result = handler.apply(chatId, update);

            assertEquals(State.NONE, result);
        }
    }

    @Test
    void testApplyWithManualButtonCallback() {
        Update update = new Update();
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData(Constants.MANUAL_BUTTON_CALLBACK);
        update.setCallbackQuery(callbackQuery);
        try (MockedStatic<TelegramUtil> telegramUtilMock = Mockito.mockStatic(TelegramUtil.class)) {
            GenerationTokenSelectionStateHandler handler = new GenerationTokenSelectionStateHandler();
            long chatId = 1L;

            State result = handler.apply(chatId, update);

            assertEquals(State.AWAITING_TOKEN, result);
        }
    }

    @Test
    void testApplyWithNoCallbackQuery() {
        Update update = new Update();
        try (MockedStatic<TelegramUtil> telegramUtilMock = Mockito.mockStatic(TelegramUtil.class)) {
            GenerationTokenSelectionStateHandler handler = new GenerationTokenSelectionStateHandler();
            long chatId = 1L;

            State result = handler.apply(chatId, update);

            assertEquals(State.GENERATION_TOKEN_SELECTION, result);
        }
    }
}