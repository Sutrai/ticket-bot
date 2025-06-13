package com.oous.ticketbot.service.bot;

import com.oous.ticketbot.service.handler.CallbackQueryHandler;
import com.oous.ticketbot.service.handler.CommandHandler;
import com.oous.ticketbot.service.handler.MessageHandler;
import com.oous.ticketbot.telegram.Bot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateDispatcher {
    private final CallbackQueryHandler queryHandler;
    private final CommandHandler commandHandler;
    private final MessageHandler messageHandler;

    public BotApiMethod<?> distribute(Update update, Bot bot) {
        if (update.hasCallbackQuery()) {
            return queryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                String text = message.getText();
                if (text.charAt(0) == '/') {
                    return commandHandler.answer(update, bot);
                }
                return messageHandler.answer(update.getMessage());
            }
        }
        return null;
    }

}
