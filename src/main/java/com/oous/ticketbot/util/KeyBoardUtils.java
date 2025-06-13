package com.oous.ticketbot.util;
import com.oous.ticketbot.data.ButtonData;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class KeyBoardUtils {

    private static InlineKeyboardButton createButton(String text, Object callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(String.valueOf(callbackData));
        return button;
    }

    private static InlineKeyboardMarkup createMarkup(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rows);
        return markup;
    }

    public static InlineKeyboardMarkup implementationOfCreateAdminMessageButton(Long formId) {
        InlineKeyboardButton accept = createButton(
                "✅ Принять",
                ButtonData.ACCEPT_BUTTON + ":" + formId
        );
        InlineKeyboardButton reject = createButton(
                "❌ Отклонить",
                ButtonData.REJECT_BUTTON + ":" + formId
        );
        return createMarkup(List.of(List.of(accept, reject)));
    }
}