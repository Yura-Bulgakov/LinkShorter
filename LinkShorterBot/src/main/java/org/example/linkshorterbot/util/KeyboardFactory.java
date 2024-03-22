package org.example.linkshorterbot.util;

import org.example.linkshorterbot.model.Constants;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {
    public static InlineKeyboardMarkup getTokenSelectionKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(Constants.AUTO_BUTTON_TEXT);
        button.setCallbackData(Constants.AUTO_BUTTON_CALLBACK);
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(Constants.MANUAL_BUTTON_TEXT);
        button2.setCallbackData(Constants.MANUAL_BUTTON_CALLBACK);
        row.add(button);
        row.add(button2);
        keyboard.add(row);

        markup.setKeyboard(keyboard);
        return markup;
    }

}
