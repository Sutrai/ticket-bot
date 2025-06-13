package com.oous.ticketbot.service.handler;

import com.oous.ticketbot.domain.entity.UserEntity;
import com.oous.ticketbot.domain.entity.UserForm;
import com.oous.ticketbot.repository.UserFormRepository;
import com.oous.ticketbot.service.bot.MessageService;
import com.oous.ticketbot.service.core.UserFormService;
import com.oous.ticketbot.service.core.UserService;
import com.oous.ticketbot.telegram.Bot;
import com.oous.ticketbot.util.ClassUtils;
import com.oous.ticketbot.util.KeyBoardUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageHandler {

    private final UserFormService userFormService;
    private final MessageService messageService;
    private final UserService userService;

    public BotApiMethod<?> answer(Message message, Bot bot) {
        Long userId = message.getFrom().getId();
        String text = message.getText();

        UserForm userForm = userFormService.findActiveFormByUserId(userId);

        if (userForm != null && userForm.isFormInProgress()) {
            return handleFormStep(userForm, text, userId, bot);
        }

        return null;
    }

    private BotApiMethod<?> handleFormStep(UserForm userForm, String text, Long  userId, Bot bot) {
        switch (userForm.getCurrentStep()) {
            case 1:
                userForm.setName(text);
                userForm.setCurrentStep(2);
                userFormService.save(userForm);
                return messageService.executeMessage(
                                "*(2/4) Сколько тебе лет?*",
                        userId,
                        null
                );

            case 2:
                try {
                    int age = Integer.parseInt(text);
                    userForm.setAge(age);
                    userForm.setCurrentStep(3);
                    userFormService.save(userForm);
                    return messageService.executeMessage(
                            "*(3/4) Расскажи, какие у тебя планы на сервер?* \uD83C\uDF0D",
                            userId,
                            null
                    );
                } catch (NumberFormatException e) {
                    return messageService.executeMessage(
                            "Введите число!",
                            userId,
                            null
                    );
                }

            case 3:
                userForm.setPlains(text);
                userForm.setCurrentStep(4);
                userFormService.save(userForm);
                return messageService.executeMessage(
                        "*(4/4) Откуда ты узнал(a) о сервере?* \uD83D\uDD0D",
                        userId,
                        null
                );

            case 4:
                userForm.setSource(text);
                userForm.setCurrentStep(0);
                userFormService.save(userForm);

                UserEntity user = userForm.getUser();
                user.setApplicationCount(user.getApplicationCount() + 1);
                userService.save(user);

                String adminMessage = "📄 *Новая заявка на вступление*\n\n" +
                        "*Пользователь:* @" + userForm.getUsername() + "\n" +
                        "*Количество заявок:* " + user.getApplicationCount() + "/3" + "\n\n" +
                        "*Информация:*\n" +
                        "   ├ *Ник:* `" + userForm.getName() + "`\n" +
                        "   ├ *Возраст:* `" + userForm.getAge() + "`\n" +
                        "   ├ *Планы на сервере:* `" + userForm.getPlains() + "`\n" +
                        "   └ *Откуда узнал о сервере:* `" + userForm.getSource() + "`";
                InlineKeyboardMarkup markup = KeyBoardUtils.implementationOfCreateAdminMessageButton(userId);
                ClassUtils.sendFormToAdmins(null, adminMessage, bot, markup);

                return messageService.executeMessage(
                        "*Заявка отправлена, ожидайте ответа от администрации в этом чате*",
                        userId,
                        null
                );

            default:
                return null;
        }
    }
}
