package com.oous.ticketbot.service.core;

import com.oous.ticketbot.domain.entity.UserForm;

public interface UserFormService {

    UserForm findByUserId(Long userId);

    void save(UserForm userForm);
}
