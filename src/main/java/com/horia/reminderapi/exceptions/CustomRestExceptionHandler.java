package com.horia.reminderapi.exceptions;

import com.horia.reminderapi.model.response.ApiResponse;
import com.horia.reminderapi.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {

        String localizedErrorMessage = ex.getLocalizedMessage();

        if (localizedErrorMessage == null) {
            localizedErrorMessage = ex.toString();
        }

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, localizedErrorMessage);

        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        String localizedErrorMessage = ex.getLocalizedMessage();

        if (localizedErrorMessage == null) {
            localizedErrorMessage = ex.toString();
        }

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, localizedErrorMessage);

        ApiResponse<ErrorMessage> apiResponse = new ApiResponse<>(errorMessage, false,
                localizedErrorMessage, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
