package com.devsuperior.dsdeliver.config;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler
{

    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request)
    {
        log.error(ex.getMessage(), ex);
        final var errrosDeValidacao = ex.getBindingResult().getFieldErrors().stream().map(
                DefaultMessageSourceResolvable::getDefaultMessage).toList();

        final var errorResponse = new ErrorResponse(errrosDeValidacao);
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({ RuntimeException.class })
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex)
    {
        log.error(ex.getMessage(), ex);
        final var errorResponse = new ErrorResponse(Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler({ IllegalStateException.class })
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex)
    {
        log.error(ex.getMessage(), ex);
        final var errorResponse = new ErrorResponse(Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex)
    {
        log.error(ex.getMessage(), ex);
        final var errorResponse = new ErrorResponse(Collections.singletonList(ex.getMessage()));
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(errorResponse);
    }
}