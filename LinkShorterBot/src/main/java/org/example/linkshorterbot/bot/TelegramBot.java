package org.example.linkshorterbot.bot;

import org.example.linkshorterbot.Command.Command;
import org.example.linkshorterbot.util.TelegramUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${path.token.generation}")
    private String tokenGenerationPath;
    @Value("${path.redirect}")
    private String redirectPath;
    private final ResponseHandler responseHandler;
    private final Map<String, Command> mapCommand;

    @Value("${bot.name}")
    private String botName;

    protected TelegramBot(Map<String, Command> mapCommand, @Value("${bot.token}") String botToken) {
        super(new String(Base64.getDecoder().decode(botToken)));
        this.mapCommand = mapCommand;
        responseHandler = new ResponseHandler(mapCommand);
        TelegramUtil.setSender(this);
        List<BotCommand> listOfCommands = mapCommand.entrySet().stream()
                .map(entry -> new BotCommand(entry.getKey(), entry.getValue().getDescription()))
                .collect(Collectors.toList());
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            responseHandler.replyHandling(update.getMessage().getChatId(), update);
        } else if (update.hasCallbackQuery()) {
            responseHandler.replyHandling(update.getCallbackQuery().getFrom().getId(), update);
        } else {
            throw new RuntimeException("Не удалось установить id чата");
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    public String getTokenGenerationPath() {
        return tokenGenerationPath;
    }

    public void setTokenGenerationPath(String tokenGenerationPath) {
        this.tokenGenerationPath = tokenGenerationPath;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public void setRedirectPath(String redirectPath) {
        this.redirectPath = redirectPath;
    }
}
