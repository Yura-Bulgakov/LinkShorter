package org.example.linkshorterbot.state;

import org.example.linkshorterbot.util.TelegramUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwaitingNameStateHandlerTest {

    @Test
    void testApplyWithName() {
        try (MockedStatic<TelegramUtil> mocked = mockStatic(TelegramUtil.class)) {
            AwaitingNameStateHandler handler = new AwaitingNameStateHandler();
            long chatId = 1L;
            String userName = "Yury";
            Message message = new Message();
            message.setText(userName);

            Update update = mock(Update.class);
            when(update.getMessage()).thenReturn(message);

            State result = handler.apply(chatId, update);
            assertEquals(State.NONE, result);
        }
    }

    @Test
    void testApplyWithoutName() {
        AwaitingNameStateHandler handler = new AwaitingNameStateHandler();
        long chatId = 1L;
        Message message = new Message();

        Update update = mock(Update.class);
        when(update.getMessage()).thenReturn(message);

        State result = handler.apply(chatId, update);
        assertEquals(State.AWAITING_NAME, result);
    }
}