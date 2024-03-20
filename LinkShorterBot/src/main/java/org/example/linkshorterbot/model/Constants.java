package org.example.linkshorterbot.model;

public class Constants {
    public static final String TOKEN_GENERATOR_PATH = "http://localhost:8080/link/creation/json";
    public static final String REDIRECT_PATH = "http://localhost:8080/r/";
    public static final String CHAT_STATES = "chatStates";

    public static final String START_DESCRIPTION = "Starts the bot";
    public static final String START_TEXT = "Добро пожаловать, вас встречает LinkShorterBot." +
            "\nПожалуйста введите ваше имя";
    public static final String HELP_DESCRIPTION = "Info about bot";
    public static final String HELP_TEXT = "Этот бот служит для создания коротких ссылок \n" +
            "Введите /start для приветствия с ботом \n" +
            "Введите /create для создания короткой ссылки \n" +
            "Введите /stop для остановки операции create \n" +
            "Введите /help для получения этого сообщения снова \n";

    public static final String STOP_DESCRIPTION = "Stop creation operation";
    public static final String STOP_TEXT = "Операция создания короткой ссылки остановлена";
    public static final String CREATE_DESCRIPTION = "Create short link";
    public static final String CREATE_TEXT = "Введите url, для которого хотите создать короткую ссылку";


    public static final String AUTO_BUTTON_TEXT = "Автоматически";
    public static final String AUTO_BUTTON_CALLBACK = "auto";

    public static final String MANUAL_BUTTON_TEXT = "Вручную";
    public static final String MANUAL_BUTTON_CALLBACK = "manual";


}
