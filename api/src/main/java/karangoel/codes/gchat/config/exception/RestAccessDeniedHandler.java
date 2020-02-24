package karangoel.codes.gchat.config.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private FilterExceptionHandler exceptionHandler;

    // == constructors ==
    @Autowired
    public RestAccessDeniedHandler() {
        this.exceptionHandler = new FilterExceptionHandler();
    }

    @Override
    public void handle(HttpServletRequest request,
                                    HttpServletResponse response,
                                    AccessDeniedException exception) throws IOException, ServletException {
      exceptionHandler
                .handleUnauthorisedAccessException(request,response,exception);
    }
}
