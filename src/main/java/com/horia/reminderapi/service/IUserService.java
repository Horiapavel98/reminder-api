package com.horia.reminderapi.service;

import com.horia.reminderapi.client.User;
import com.horia.reminderapi.client.UserDto;
import com.horia.reminderapi.exceptions.UserAlreadyExistException;

public interface IUserService {
    User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException;
}
