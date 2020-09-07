package com.horia.reminderapi.service;

import com.horia.reminderapi.client.User;
import com.horia.reminderapi.client.UserDto;
import com.horia.reminderapi.exceptions.ResourceNotFoundException;
import com.horia.reminderapi.exceptions.UserAlreadyExistException;
import com.horia.reminderapi.model.Responsible;
import com.horia.reminderapi.model.response.ApiResponse;

import java.util.List;

public interface IUserService {

    ApiResponse<? extends Responsible> registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException;

    ApiResponse<? extends Responsible> updateUser(long id, UserDto userDto);

    ApiResponse<? extends Responsible> getAllUsers();

    ApiResponse<? extends Responsible> getUserById(long id) throws ResourceNotFoundException;

    ApiResponse<? extends Responsible> deleteUser(long id) throws ResourceNotFoundException;
}
