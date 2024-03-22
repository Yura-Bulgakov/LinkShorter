package org.example.linkshorterbot.Command;

import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.state.State;
import org.example.linkshorterbot.util.TelegramUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class CreateCommandTest {

    @Test
    public void testHandleCommand() {
        try (MockedStatic<TelegramUtil> mocked = mockStatic(TelegramUtil.class)) {
            doNothing().when(TelegramUtil.class);

            CreateCommand createCommand = new CreateCommand();
            State result = createCommand.handleCommand(1L);
            assertEquals(result, State.AWAITING_URL);
        }
    }

    @Test
    public void testGetDescription() {
        CreateCommand createCommand = new CreateCommand();
        assert createCommand.getDescription().equals(Constants.CREATE_DESCRIPTION);
    }
}