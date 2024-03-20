package org.example.linkshorterbot.state;

import org.example.linkshorterbot.model.RequestContainer;
import org.example.linkshorterbot.model.TokenCreationRequest;
import org.example.linkshorterbot.util.TelegramUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingTokenStateHandlerTest {

    @Test
    void testApplyWithTextMessage() {
        try (MockedStatic<TelegramUtil> mocked = mockStatic(TelegramUtil.class)) {
            AwaitingTokenStateHandler handler = new AwaitingTokenStateHandler();
            long chatId = 1L;
            String token = "token";
            Message message = new Message();
            message.setText(token);
            TokenCreationRequest request = new TokenCreationRequest();
            request.setToken(token);
            Update update =  new Update();
            update.setMessage(message);
            RequestContainer.container.put(chatId, request);

            when(TelegramUtil.postRequestForToken(chatId, request)).thenReturn(State.NONE);

            State result = handler.apply(chatId, update);

            assertEquals(State.NONE, result);
        }
    }

    @Test
    void testApplyWithoutTextMessage() {
        try (MockedStatic<TelegramUtil> mocked = mockStatic(TelegramUtil.class)) {
            AwaitingTokenStateHandler handler = new AwaitingTokenStateHandler();
            long chatId = 1L;
            Message message = new Message();
            Update update =  new Update();
            update.setMessage(message);

            State result = handler.apply(chatId, update);

            assertEquals(result, State.AWAITING_TOKEN);
        }
    }

    @Test
    void testApplyWithoutMessage() {
        try (MockedStatic<TelegramUtil> mocked = mockStatic(TelegramUtil.class)) {
            AwaitingTokenStateHandler handler = new AwaitingTokenStateHandler();
            long chatId = 1L;
            Update update = new Update();

            State result = handler.apply(chatId, update);

            assertEquals(result, State.AWAITING_TOKEN);
        }
    }
}