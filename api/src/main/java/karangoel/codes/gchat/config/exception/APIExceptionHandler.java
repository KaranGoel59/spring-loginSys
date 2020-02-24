package karangoel.codes.gchat.config.exception;

import karangoel.codes.gchat.util.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage());

        FieldError fieldError = ex.getBindingResult().getFieldError();

        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
            .status(status.toString())
            .message(fieldError != null ? fieldError.getDefaultMessage() : "error").build();

;;        return ResponseEntity.badRequest().body(responseDTO);
    };

    // == custom handler ==

    @ExceptionHandler( { ConstraintViolationException.class } )
    public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());

        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
            .status(HttpStatus.BAD_REQUEST.toString())
            .message(ex.getMessage()).build();

        return ResponseEntity.badRequest().body(responseDTO);
    }
}
