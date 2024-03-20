package org.example.linkshorterbot.Command;

import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.state.State;
import org.example.linkshorterbot.util.TelegramUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class StopCommandTest {

    @Test
    public void testHandleCommand() {
        try (MockedStatic<TelegramUtil> mocked = mockStatic(TelegramUtil.class)) {
            StopCommand stopCommand = new StopCommand();
            long chatId = 1L;
            State result = stopCommand.handleCommand(chatId);
            assertEquals(result, State.NONE);
        }
    }

    @Test
    public void testGetDescription() {
        StopCommand stopCommand = new StopCommand();
        assertEquals(Constants.STOP_DESCRIPTION, stopCommand.getDescription());
    }
}