package org.example.linkshorterbot.util;

import org.example.linkshorterbot.bot.TelegramBot;
import org.example.linkshorterbot.model.Constants;
import org.example.linkshorterbot.model.TokenCreationRequest;
import org.example.linkshorterbot.state.State;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramUtil {
    private static TelegramBot bot;
    private static RestTemplate restTemplate = new RestTemplate();

    public static void setSender(TelegramBot bot) {
        TelegramUtil.bot = bot;
    }

    public static void executeSendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public static void promptWithKeyboard(long chatId, String message, InlineKeyboardMarkup keyboard) {
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        sendMessage.setReplyMarkup(keyboard);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static State postRequestForToken(long chatId, TokenCreationRequest request) {
        String serviceResponse = "";
        try {
            serviceResponse = restTemplate.postForObject(Constants.TOKEN_GENERATOR_PATH, request, String.class);
        } catch (Exception e) {
            TelegramUtil.executeSendMessage(chatId, "Ошибка при запросе к сервису");
        }
        if (serviceResponse != null && serviceResponse.startsWith("Токен")) {
            TelegramUtil.executeSendMessage(chatId, serviceResponse +
                    "\nДля перехода по токену воспользуйтесь ссылкой: \n" +
                    Constants.REDIRECT_PATH + serviceResponse.split(" ")[1]);
            return State.NONE;
        } else {
            TelegramUtil.promptWithKeyboard(chatId, "Не удалось создать токен. Попробуйте снова.",
                    KeyboardFactory.getTokenSelectionKeyboard());
            return State.GENERATION_TOKEN_SELECTION;
        }
    }
}
