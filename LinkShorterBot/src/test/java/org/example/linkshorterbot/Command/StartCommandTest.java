package org.example.linkshorterbot.Command;

import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.state.State;
import org.example.linkshorterbot.util.TelegramUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class StartCommandTest {

    @Test
    public void testHandleCommand() {
        try (MockedStatic<TelegramUtil> mocked = mockStatic(TelegramUtil.class)) {
            StartCommand startCommand = new StartCommand();
            long chatId = 1L;

            State result = startCommand.handleCommand(chatId);
            assertEquals(result, State.AWAITING_NAME);
        }
    }

    @Test
    public void testGetDescription() {
        StartCommand startCommand = new StartCommand();
        assertEquals(Constants.START_DESCRIPTION, startCommand.getDescription());
    }
}
