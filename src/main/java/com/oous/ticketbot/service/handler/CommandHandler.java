package com.oous.ticketbot.service.handler;

import com.oous.ticketbot.data.CommandData;
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
        CommandData commandData;

        try {
            commandData = CommandData.valueOf(command);
        } catch (Exception e) {
            log.warn("Unsupported command was received: {}", command);
            return null; //TODO
        }

        switch (commandData) {
            case start -> {
                userRepository.save(dataService.insertNewUser(update.getMessage()));
                return messageService.executeMessage(
                        "privet",
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