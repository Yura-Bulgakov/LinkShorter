package org.example.linkshorterbot.state;

import org.example.linkshorterbot.util.TelegramUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoneStateHandlerTest {

    @Test
    void testApplyWithMessage() {
        NoneStateHandler handler = new NoneStateHandler();
        long chatId = 1L;
        String anyMsg = "anymsg";
        Message message = new Message();
        message.setText(anyMsg);
        Update update = new Update();
        update.setMessage(message);
        try (MockedStatic<TelegramUtil> telegramUtilMock = Mockito.mockStatic(TelegramUtil.class)) {
            State result = handler.apply(chatId, update);
            assertEquals(State.NONE, result);
        }
    }

    @Test
    void testApplyWithoutMessage() {
        NoneStateHandler handler = new NoneStateHandler();
        long chatId = 1L;
        Update update = new Update();
        try (MockedStatic<TelegramUtil> telegramUtilMock = Mockito.mockStatic(TelegramUtil.class)) {
            State result = handler.apply(chatId, update);

            assertEquals(State.NONE, result);
        }
    }
}