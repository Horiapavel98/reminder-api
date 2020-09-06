package com.horia.reminderapi.exceptions;

import com.horia.reminderapi.model.response.ApiResponse;
import com.horia.reminderapi.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResponse<ErrorMessage>> handleAnyException(Exception ex) {

        String localizedErrorMessage = getLocalizedErrorMessageOrExceptionDescription(ex);

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, localizedErrorMessage);

        ApiResponse<ErrorMessage> apiResponse = new ApiResponse<>(errorMessage, false,
                localizedErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(
                apiResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ApiResponse<ErrorMessage>> handleResourceNotFoundException(ResourceNotFoundException ex) {

        String localizedErrorMessage = getLocalizedErrorMessageOrExceptionDescription(ex);

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, localizedErrorMessage);

        ApiResponse<ErrorMessage> apiResponse = new ApiResponse<>(errorMessage, false,
                localizedErrorMessage, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<ApiResponse<ErrorMessage>> handleUserAlreadyExistException(UserAlreadyExistException ex) {

        String localizedErrorMessage = getLocalizedErrorMessageOrExceptionDescription(ex);

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, localizedErrorMessage);

        ApiResponse<ErrorMessage> apiResponse = new ApiResponse<>(errorMessage, false,
                localizedErrorMessage, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String localizedErrorMessage = getLocalizedErrorMessageOrExceptionDescription(ex);

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, localizedErrorMessage);

        ApiResponse<ErrorMessage> apiResponse = new ApiResponse<>(errorMessage, false,
                localizedErrorMessage, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                apiResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }

    private String getLocalizedErrorMessageOrExceptionDescription(Exception ex) {
        if (ex.getLocalizedMessage() == null) {
            return ex.toString();
        }
        return ex.getLocalizedMessage();
    }
}
