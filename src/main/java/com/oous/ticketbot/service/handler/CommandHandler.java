package com.oous.ticketbot.service.handler;

import com.oous.ticketbot.data.CommandData;
import com.oous.ticketbot.domain.entity.UserEntity;
import com.oous.ticketbot.domain.entity.UserForm;
import com.oous.ticketbot.repository.UserRepository;
import com.oous.ticketbot.service.bot.MessageService;
import com.oous.ticketbot.service.core.DataService;
import com.oous.ticketbot.service.core.UserFormService;
import com.oous.ticketbot.telegram.Bot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandHandler {

    private final UserFormService userFormService;
    private final MessageService messageService;
    private final UserRepository userRepository;
    private final DataService dataService;

    public BotApiMethod<?> answer(Update update, Bot bot) {
        String command =  update.getMessage().getText().substring(1);
        Long userId = update.getMessage().getFrom().getId();
        String userName = update.getMessage().getFrom().getUserName();
        CommandData commandData;

        try {
            commandData = CommandData.valueOf(command);
        } catch (Exception e) {
            log.warn("Unsupported command was received: {}", command);
            return null; //TODO
        }

        switch (commandData) {
            case start -> {
                 UserEntity user = userRepository.save(dataService.insertNewUser(update.getMessage()));

                if (user.getApplicationCount() >= 3) {
                    return messageService.executeMessage(
                            "❌ Лимит заявок исчерпан (максимум 3).",
                            update.getMessage().getChatId(),
                            null
                    );
                }

                UserForm userForm = userFormService.findByUserId(userId);
                if (userForm == null) {
                    userForm = new UserForm();
                    userForm.setUsername(userName);
                    userForm.setUser(user);
                }
                userForm.setCurrentStep(1);
                userFormService.save(userForm);

                return messageService.executeMessage(
                    "\uD83C\uDF1F Здесь ты можешь оставить заявку на сервер *ISLAND OF 44*.\n" +
                        "У тебя есть **3 попытки** на корректное заполнение.\n" +
                        "Если заявка будет оформлена неверно, *она будет отклонена.*\n\n" +
                        "*(1/4) Введите ваш ник в майнкрафте:*",
                        update.getMessage().getChatId(),
                        null
                );
            }

            default -> {
                log.warn("Unhandled command: {}", commandData);
                throw new UnsupportedOperationException();
            }
        }
    }
}