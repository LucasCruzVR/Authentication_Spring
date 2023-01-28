package com.castgroup.auth.api.controller.exception;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "com.auth.api")
public class RestHandlerException extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleNullPointer(HttpServletRequest request, IllegalArgumentException exception) {
        StandardError response = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY, "exception.getMessage()");
        response.setMessage("The row for address is not existent: " + request.getRequestURI());
        return buildResponseEntity(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(HttpServletResponse response, AuthenticationException exception) {
        StandardError r = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY, "exception.getMessage()");
        r.setMessage("The row for address is not existent: " );
        return buildResponseEntity(r);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        StandardError r = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY, "exception.getMessage()");
        r.setMessage("The row for address is not existent: " );
        return buildResponseEntity(r);
    }

    private ResponseEntity<Object> buildResponseEntity(StandardError errorResponse){
        return new ResponseEntity<Object>(errorResponse, errorResponse.getStatusHttp());
    }
}
