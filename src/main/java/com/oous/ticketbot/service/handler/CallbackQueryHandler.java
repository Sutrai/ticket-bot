package com.oous.ticketbot.service.handler;

import com.oous.ticketbot.telegram.Bot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackQueryHandler {

    public BotApiMethod<?> answer(CallbackQuery query, Bot bot) {
        return null;
    }
}