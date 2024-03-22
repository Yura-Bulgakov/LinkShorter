package org.example.linkshorterbot.model;

public class Constants {
    public static final String TOKEN_GENERATOR_PATH = "http://localhost:8080/link/creation/json";
    public static final String REDIRECT_PATH = "http://127.0.0.1:8080/r/";

    public static final String START_DESCRIPTION = "Начать диалог с ботом";
    public static final String START_TEXT = "Добро пожаловать, вас встречает LinkShorterBot." +
            "\nПожалуйста введите ваше имя";
    public static final String HELP_DESCRIPTION = "Информация о боте";
    public static final String HELP_TEXT = "Этот бот служит для создания коротких ссылок \n" +
            "Введите /start для приветствия с ботом \n" +
            "Введите /create для создания короткой ссылки \n" +
            "Введите /stop для остановки операции create \n" +
            "Введите /help для получения этого сообщения снова \n";

    public static final String STOP_DESCRIPTION = "Остановить текущую операцию";
    public static final String STOP_TEXT = "Операция остановлена";
    public static final String CREATE_DESCRIPTION = "Создать короткую ссылку";
    public static final String CREATE_TEXT = "Введите url, для которого хотите создать короткую ссылку";


    public static final String AUTO_BUTTON_TEXT = "Автоматически";
    public static final String AUTO_BUTTON_CALLBACK = "auto";

    public static final String MANUAL_BUTTON_TEXT = "Вручную";
    public static final String MANUAL_BUTTON_CALLBACK = "manual";


}
