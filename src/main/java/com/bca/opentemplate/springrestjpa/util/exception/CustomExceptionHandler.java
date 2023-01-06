package com.bca.opentemplate.springrestjpa.util.exception;

import java.net.SocketTimeoutException;
import java.util.Set;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bca.opentemplate.springrestjpa.configuration.logging.BaseLogging;
import com.bca.opentemplate.springrestjpa.model.dto.eai.EaiResponseDto;
import com.bca.opentemplate.springrestjpa.util.http.HttpResponseMapping;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class CustomExceptionHandler extends BaseLogging {

    @Order(value = 1)
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> customExceptionHandler(CustomException ex, WebRequest request) {
        transLog.error("CustomException: " + ex.getLocalizedMessage());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return ResponseEntity.status(ex.getEaiResponseDto().getHttpStatus()).body(ex.getEaiResponseDto());
    }

    @Order(value = 2)
    @ExceptionHandler({ InvalidFormatException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, WebRequest request) {
        transLog.error("Error : {} should be {}", ex.getValue(), ex.getTargetType());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return ResponseEntity.badRequest().body(new EaiResponseDto<Object>(HttpResponseMapping.BAD_REQUEST));
    }
    
    @Order(value = 3)
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
    WebRequest request) {
        transLog.error("Error Argument Missmatch: {}", ex.getName());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return ResponseEntity.badRequest().body(new EaiResponseDto<Object>(HttpResponseMapping.BAD_REQUEST));
    }
    
    @Order(value = 4)
    @ExceptionHandler({ NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
    HttpStatus status, WebRequest request) {
        transLog.error("Error : No handler found for {} {}", ex.getHttpMethod(), ex.getRequestURL());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EaiResponseDto<Object>(HttpResponseMapping.NOT_FOUND));
    }
    
    @Order(value = 5)
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
    HttpHeaders headers, HttpStatus status, WebRequest request) {
        transLog.error(ex.getMessage());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request.");
        Set<HttpMethod> supportedHttpMethods = ex.getSupportedHttpMethods();
        if(supportedHttpMethods!=null) {
            builder.append(" Supported methods are: ");
            supportedHttpMethods.forEach(t -> builder.append(t + " "));
        }
        
        transLog.error("Error : {}", builder);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(new EaiResponseDto<Object>(HttpResponseMapping.METHOD_NOT_ALLOWED));
    }
    
    @Order(value = 6)
    @ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
    HttpHeaders headers, HttpStatus status, WebRequest request) {
        transLog.error(ex.getMessage());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        transLog.error("Error : {}", builder);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new EaiResponseDto<Object>(HttpResponseMapping.UNSSUPPORTED_MEDIA_TYPE));
    }

    @Order(value = 7)
    @ExceptionHandler({ MissingServletRequestParameterException.class })
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
    HttpHeaders headers, HttpStatus status, WebRequest request) {
        transLog.error(ex.getMessage());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());

        return ResponseEntity.badRequest().body(new EaiResponseDto<Object>(HttpResponseMapping.METHOD_NOT_ALLOWED));
    }

    @Order(value = 8)
    @ExceptionHandler({ ResourceAccessException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleResourceAccess(Exception ex, WebRequest request) {
        transLog.error(ex.getMessage());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new EaiResponseDto<Object>(HttpResponseMapping.UNAUTHORIZED));
    }
    
    @Order(value = 9)
    @ExceptionHandler({ SocketTimeoutException.class })
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ResponseEntity<Object> handleTimeout(Exception ex, WebRequest request) {
        transLog.error(ex.getMessage());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
        .body(new EaiResponseDto<Object>(HttpResponseMapping.REQUEST_TIMEOUT));
    }
    
    @Order(Ordered.LOWEST_PRECEDENCE)
    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        transLog.error(ex.getMessage());
        verboseLog.error("Exception: {}:{}:{}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new EaiResponseDto<Object>(HttpResponseMapping.INTERNAL_SERVER_ERROR));
    }
}
