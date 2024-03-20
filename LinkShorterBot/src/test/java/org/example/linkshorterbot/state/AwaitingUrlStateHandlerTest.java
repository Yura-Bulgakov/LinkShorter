package org.example.linkshorterbot.state;

import org.example.linkshorterbot.model.RequestContainer;
import org.example.linkshorterbot.model.TokenCreationRequest;
import org.example.linkshorterbot.util.TelegramUtil;
import org.example.linkshorterbot.util.UrlValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AwaitingUrlStateHandlerTest {

    @Test
    void testApplyWithValidUrl() {
        try (MockedStatic<TelegramUtil> mocked = Mockito.mockStatic(TelegramUtil.class);
             MockedStatic<UrlValidator> mocked2 = Mockito.mockStatic(UrlValidator.class)) {
            AwaitingUrlStateHandler handler = new AwaitingUrlStateHandler();
            long chatId = 1L;
            String validUrl = "https://abc.com";
            Message message = new Message();
            message.setText(validUrl);
            Update update = new Update();
            update.setMessage(message);

            when(UrlValidator.isValid(validUrl)).thenReturn(true);

            State result = handler.apply(chatId, update);
            assertEquals(State.GENERATION_TOKEN_SELECTION, result);
        }
    }

    @Test
    void testApplyWithInvalidUrl() {
        try (MockedStatic<TelegramUtil> mocked = Mockito.mockStatic(TelegramUtil.class);
             MockedStatic<UrlValidator> mocked2 = Mockito.mockStatic(UrlValidator.class)) {
            AwaitingUrlStateHandler handler = new AwaitingUrlStateHandler();
            long chatId = 1L;
            String invalidUrl = "invalidurl";
            Message message = new Message();
            message.setText(invalidUrl);
            Update update = new Update();
            update.setMessage(message);

            when(UrlValidator.isValid(invalidUrl)).thenReturn(false);

            State result = handler.apply(chatId, update);
            assertEquals(State.AWAITING_URL, result);
        }
    }
}