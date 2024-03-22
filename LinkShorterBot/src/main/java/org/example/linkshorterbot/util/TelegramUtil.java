package org.example.linkshorterbot.util;

import org.example.linkshorterbot.bot.TelegramBot;
import org.example.linkshorterbot.model.TokenCreationRequest;
import org.example.linkshorterbot.state.State;
import org.springframework.web.client.RestClientException;
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
        sendMessage.enableHtml(true);
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
        try {
            String response = restTemplate.postForObject(bot.getTokenGenerationPath(), request, String.class);
            if (response != null && response.startsWith("Токен")) {
                String redirectStr = bot.getRedirectPath() + response.split(" ")[1];
                String hrefStr = String.format("<a href='%s'>%s</a>", redirectStr, redirectStr);
                executeSendMessage(chatId, response +
                        "\nДля перехода по токену воспользуйтесь ссылкой: \n" + hrefStr);
            }
            return State.NONE;
        } catch (RestClientException e) {
            try {
                executeSendMessage(chatId, extractErrorMessage(e.getMessage()));
                promptWithKeyboard(chatId, "Не удалось создать токен. Попробуйте снова.",
                        KeyboardFactory.getTokenSelectionKeyboard());
                return State.GENERATION_TOKEN_SELECTION;
            } catch (Exception ex) {
                executeSendMessage(chatId, "Не удалось обработать ответ от сервиса");
                return State.NONE;
            }
        }
    }

    private static String extractErrorMessage(String errorResponse) {
        String[] errorParts = errorResponse.split("\"defaultMessage\":");
        String lastPart = errorParts[errorParts.length - 1];
        return lastPart.split("\"")[1];
    }
}
