package com.horia.reminderapi.service;

import com.horia.reminderapi.client.User;
import com.horia.reminderapi.client.UserDto;
import com.horia.reminderapi.exceptions.ResourceNotFoundException;
import com.horia.reminderapi.exceptions.UserAlreadyExistException;
import com.horia.reminderapi.model.Responsible;
import com.horia.reminderapi.model.response.ApiResponse;
import com.horia.reminderapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public ApiResponse<? extends Responsible> registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {

        if(emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("User with e-mail " + userDto.getEmail() + " already exists");
        }

        final User user = new User();

        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());

        repository.save(user);

        return new ApiResponse<>(user, true, "User saved successfully", HttpStatus.OK);
    }

    @Override
    public ApiResponse<? extends Responsible> getUserById(long id) throws ResourceNotFoundException {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User userToReturn = userOptional.get();
            return new ApiResponse<>(userToReturn, true,
                    "User retrieved successfully", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User with id=" + id + " not found");
        }
    }

    @Override
    public ApiResponse<? extends Responsible> deleteUser(long id) throws ResourceNotFoundException {
        Optional<User> userOptional = repository.findById(id);

        if (userOptional.isPresent()) {
            User userToReturn = userOptional.get();

            repository.delete(userOptional.get());

            return new ApiResponse<>(userToReturn, true,
                    "User deleted successfully", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User with id=" + id + " not found");
        }
    }


    private boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }
}
