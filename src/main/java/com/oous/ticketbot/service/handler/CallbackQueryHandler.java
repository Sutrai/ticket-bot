package com.oous.ticketbot.service.handler;

import com.oous.ticketbot.data.ButtonData;
import com.oous.ticketbot.domain.entity.UserForm;
import com.oous.ticketbot.service.bot.MessageService;
import com.oous.ticketbot.service.core.UserFormService;
import com.oous.ticketbot.telegram.Bot;
import com.oous.ticketbot.util.ClassUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallbackQueryHandler {

    private final UserFormService userFormService;
    private final MessageService messageService;

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();
        System.out.println(chatId);

        if (data.startsWith(String.valueOf(ButtonData.ACCEPT_BUTTON))) {
            return handleAccept(callbackQuery, data.split(":")[1], bot);
        } else if (data.startsWith(String.valueOf(ButtonData.REJECT_BUTTON))) {
            return handleReject(callbackQuery, data.split(":")[1], bot);
        }
        return null;
    }

    private BotApiMethod<?> handleAccept(CallbackQuery callbackQuery, String formId, Bot bot) {
        UserForm form = userFormService.findByUserId(Long.parseLong(formId));
        String adminMessage = String.format("✅ @%s *одобрил заявку*", callbackQuery.getFrom().getUserName());
        ClassUtils.sendFormToAdmins(callbackQuery, adminMessage, bot, null);

        return messageService.executeMessage(
                "🎉 *Ваша заявка принята! Можете заходить на сервер*",
                form.getUser().getTelegramId(),
                null
        );
    }

    private BotApiMethod<?> handleReject(CallbackQuery callbackQuery, String formId, Bot bot) {
        UserForm form = userFormService.findByUserId(Long.parseLong(formId));

        String adminMessage = String.format("❌ @%s *отклонил заявку*", callbackQuery.getFrom().getUserName());
        ClassUtils.sendFormToAdmins(callbackQuery, adminMessage, bot, null);

        return messageService.executeMessage(
                "❌ *Ваша заявка была отклонена модератором.*",
                form.getUser().getTelegramId(),
                null
        );
    }
}