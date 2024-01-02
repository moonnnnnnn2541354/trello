package com.sparta.trellor.global.exception;

import com.sparta.trellor.domain.column.dto.MessageDto;
import com.sparta.trellor.global.exception.error.CommonErrorCode;
import com.sparta.trellor.global.exception.error.ErrorCode;
import com.sparta.trellor.global.exception.error.ErrorResponse;
import com.sparta.trellor.global.exception.error.ErrorResponse.ValidationError;
import com.sparta.trellor.global.exception.error.RestApiException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DuplicateUsernameException.class})
    public ResponseEntity<MessageDto> handleException(DuplicateUsernameException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
            // HTTP body
            restApiException,
            // HTTP status code
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({FormNotMatchException.class})
    public ResponseEntity<MessageDto> handleException(FormNotMatchException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
            // HTTP body
            restApiException,
            // HTTP status code
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({TokenNotValidException.class})
    public ResponseEntity<MessageDto> handleException(TokenNotValidException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
            // HTTP body
            restApiException,
            // HTTP status code
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<MessageDto> handleException(IllegalArgumentException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
            // HTTP body
            restApiException,
            // HTTP status code
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({WriterNotMatchException.class})
    public ResponseEntity<MessageDto> handleException(WriterNotMatchException ex) {
        MessageDto restApiException = new MessageDto(ex.getMessage());
        return new ResponseEntity<>(
            // HTTP body
            restApiException,
            // HTTP status code
            HttpStatus.BAD_REQUEST
        );
    }

    ///////////////////////////////////////////////////
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {
        log.warn("handleIllegalArgument", ex);
        CommonErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(ex, errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .status(errorCode.getHttpStatus().value())
            .message(errorCode.getMessage())
            .build();
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(e, errorCode));
    }

    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ValidationError> validationErrorList = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(ErrorResponse.ValidationError::of)
            .collect(Collectors.toList());

        return ErrorResponse.builder()
            .code(errorCode.name())
            .status(errorCode.getHttpStatus().value())
            .message(errorCode.getMessage())
            .errors(validationErrorList)
            .build();
    }
}
