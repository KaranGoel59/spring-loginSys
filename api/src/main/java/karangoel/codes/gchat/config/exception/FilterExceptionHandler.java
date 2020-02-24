package karangoel.codes.gchat.config.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import karangoel.codes.gchat.util.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class FilterExceptionHandler {
    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
    public final void handleUnauthorisedAccessException(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception ex) {
        log.error(ex.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .status(HttpStatus.FORBIDDEN.toString())
                .message("unauthorized")
                .body(ex.getMessage())
                .build();

        try {
            OutputStream out = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out,   ResponseEntity.badRequest().body(responseDTO));
            out.flush();
        } catch (IOException eio) {
            throw new RuntimeException(eio.getMessage());
        }
    }
}
