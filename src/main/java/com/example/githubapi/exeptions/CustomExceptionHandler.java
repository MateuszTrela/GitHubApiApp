package com.example.githubapi.exeptions;

import com.example.githubapi.payload.ErrorDetails;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleGResourceNotFoundException(ResourceNotFoundException exception,
                                                                 WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
            HttpMediaTypeNotAcceptableException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(status.value(), exception.getMessage());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(errorDetails, responseHeaders, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException exception,
                                                                      WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ErrorDetails> handleHttpClientForbiddenErrorException(HttpClientErrorException.Forbidden exception,
                                                                         WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.FORBIDDEN.value(),
                exception.getStatusText());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
