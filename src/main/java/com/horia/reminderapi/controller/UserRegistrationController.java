package com.horia.reminderapi.controller;

import com.horia.reminderapi.client.User;
import com.horia.reminderapi.client.UserDto;
import com.horia.reminderapi.exceptions.ResourceNotFoundException;
import com.horia.reminderapi.model.Responsible;
import com.horia.reminderapi.model.response.ApiResponse;
import com.horia.reminderapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Validated
public class UserRegistrationController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest webRequest, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("user/registration")
    @ResponseBody
    public ResponseEntity<?> registerUserAccount(@Valid @RequestBody UserDto accountDto,
                                                 final HttpServletRequest request, Errors errors) {
        if (errors.hasErrors()){
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok().body(userService.registerNewUserAccount(accountDto));
    }

    @DeleteMapping("user/deletion/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(this.userService.deleteUser(id));
    }
}
