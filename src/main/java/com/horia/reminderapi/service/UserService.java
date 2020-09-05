package com.horia.reminderapi.service;

import com.horia.reminderapi.client.User;
import com.horia.reminderapi.client.UserDto;
import com.horia.reminderapi.exceptions.UserAlreadyExistException;
import com.horia.reminderapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if(emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("User with e-mail " + userDto.getEmail() + " already exists");
        }

        final User user = new User();

        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());

        return repository.save(user);
    }

    private boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }
}
