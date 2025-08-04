package com.Ketan.UberApp.advices;

import com.Ketan.UberApp.exceptions.ConflictExceptions;
import com.Ketan.UberApp.exceptions.InsufficientFunds;
import com.Ketan.UberApp.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictExceptions.class)
    public ResponseEntity<ApiResponse<?>> handleRunTimeConflictExceptions(ConflictExceptions ex){
        ApiError error = ApiError
                .builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getLocalizedMessage())
                .build();
        return buildApiResponse(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundExceptions(ResourceNotFoundException ex){
        ApiError error = ApiError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getLocalizedMessage())
                .build();
        return buildApiResponse(error);
    }

    @ExceptionHandler(InsufficientFunds.class)
    public ResponseEntity<ApiResponse<?>> handleInsufficientFundsExceptions(InsufficientFunds ex){
        ApiError error = ApiError
                .builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getLocalizedMessage())
                .build();
        return buildApiResponse(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAnyOtherError(Exception ex){
        ApiError error = ApiError
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ex.getLocalizedMessage())
                .build();
        return buildApiResponse(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException MANVE){
        List<String> errors = MANVE
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .toList();
        ApiError error = ApiError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed")
                .subErrors(errors)
                .build();
        return buildApiResponse(error);
    }

    public ResponseEntity<ApiResponse<?>> buildApiResponse(ApiError error){
        return new ResponseEntity<>(new ApiResponse<>(error),error.getStatus());
    }
}
