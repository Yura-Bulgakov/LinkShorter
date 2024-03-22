package org.example.linkshorterbot.state;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiFunction;

public enum State {
    NONE(new NoneStateHandler()),
    AWAITING_NAME(new AwaitingNameStateHandler()),
    AWAITING_URL(new AwaitingUrlStateHandler()),
    GENERATION_TOKEN_SELECTION(new GenerationTokenSelectionStateHandler()),
    AWAITING_TOKEN(new AwaitingTokenStateHandler()),
    ;
    private final BiFunction<Long, Update, State> stateHandler;

    State(BiFunction<Long, Update, State> stateHandler) {
        this.stateHandler = stateHandler;
    }

    public State handle(long chatId, Update update) {
        return stateHandler.apply(chatId, update);
    }
}
