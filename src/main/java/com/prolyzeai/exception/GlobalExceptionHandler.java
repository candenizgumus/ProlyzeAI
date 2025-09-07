package com.prolyzeai.exception;

// Bu sınıf tüm controller sınıfları için merkezi bir şekilde hata yönetimi sağlayacaktır.

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(RuntimeException.class)//
    public ResponseEntity<String> handleException(RuntimeException ex)
    {
        return ResponseEntity.badRequest()
                .body("Uygulamada RunTime Exception oluştu................" + ex.getMessage());
    }


    @ExceptionHandler(ProlyzeException.class)
    public ResponseEntity<ErrorMessage> handleDemoException(ProlyzeException ex)
    {
        ErrorType errorType = ex.getErrorType();
        return new ResponseEntity(createErrorMessage(ex,
                errorType),
                errorType.getHttpStatus());
    }

    private ErrorMessage createErrorMessage(Exception ex, ErrorType errorType) {
        String customMessage = ex.getMessage();

        // Eğer customMessage errorType.getMessage() ile aynı ise tekrarı önle
        String fullMessage;
        if (customMessage != null && !customMessage.equals(errorType.getMessage())) {
            fullMessage = errorType.getMessage() + " - " + customMessage;
        } else {
            fullMessage = errorType.getMessage();
        }

        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(fullMessage)
                .build();
    }


    private ErrorMessage createErrorMessage(MethodArgumentNotValidException ex) {


        List<String> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code(ex.hashCode())
                .message(fieldErrors.toString()) // Or any general message
                .build();

        errorMessage.setFields(fieldErrors);
        return errorMessage;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception)
    {

        ErrorType errorType = ErrorType.BAD_REQUEST_ERROR;
        List<String> fields = new ArrayList<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
        ErrorMessage errorMessage = createErrorMessage(exception);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(errorMessage,
                errorType.getHttpStatus());
    }
}