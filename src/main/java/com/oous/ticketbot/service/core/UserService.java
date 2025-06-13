package com.oous.ticketbot.service.core;

import com.oous.ticketbot.domain.entity.UserEntity;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface UserService {

    UserEntity insertNewUser(Message message);
}