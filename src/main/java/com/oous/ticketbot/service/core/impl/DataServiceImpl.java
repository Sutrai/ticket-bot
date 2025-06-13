package com.oous.ticketbot.service.core.impl;

import com.oous.ticketbot.domain.entity.UserEntity;
import com.oous.ticketbot.service.core.DataService;
import com.oous.ticketbot.service.core.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final UserService userService;

    @Override
    public UserEntity insertNewUser(Message message) {
        return userService.insertNewUser(message);
    }
}
