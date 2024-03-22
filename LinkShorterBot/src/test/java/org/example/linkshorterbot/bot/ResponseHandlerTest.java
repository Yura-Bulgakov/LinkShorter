package org.example.linkshorterbot.bot;

import org.example.linkshorterbot.Command.Command;
import org.example.linkshorterbot.state.State;
import org.example.linkshorterbot.util.TelegramUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResponseHandlerTest {

    @Test
    void testReplyHandlingWithCommand() {
        Command command = mock(Command.class);
        when(command.handleCommand(anyLong())).thenReturn(State.AWAITING_NAME);

        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("/test", command);

        ResponseHandler responseHandler = new ResponseHandler(commandMap);

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn("/test");
        try (MockedStatic<TelegramUtil> telegramUtilMock = Mockito.mockStatic(TelegramUtil.class)) {
            responseHandler.replyHandling(1L, update);
            verify(command, times(1)).handleCommand(anyLong());
        }
    }

    @Test
    void testReplyHandlingWithInvalidCommand() {
        Command command = mock(Command.class);
        Map<String, Command> commandMap = new HashMap<>();
        ResponseHandler responseHandler = new ResponseHandler(commandMap);
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn("/nonExistingCommand");

        try (MockedStatic<TelegramUtil> telegramUtilMock = Mockito.mockStatic(TelegramUtil.class)) {
            responseHandler.replyHandling(1L, update);

            verify(command, never()).handleCommand(anyLong());
        }
    }

    @Test
    void testHandleState() {
        State mockState = mock(State.class);
        Update mockUpdate = mock(Update.class);
        Map<Long, State> stateMap = new HashMap<>();
        stateMap.put(1L, mockState);

        ResponseHandler responseHandler = new ResponseHandler(new HashMap<>());
        responseHandler.getChatStates().putAll(stateMap);
        try (MockedStatic<TelegramUtil> telegramUtilMock = Mockito.mockStatic(TelegramUtil.class)) {
            responseHandler.replyHandling(1L, mockUpdate);

            verify(mockState, times(1)).handle(anyLong(), any());
        }
    }

}