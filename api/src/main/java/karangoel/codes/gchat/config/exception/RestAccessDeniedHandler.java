package karangoel.codes.gchat.config.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private final FilterExceptionHandler exceptionHandler;

    // == constructors ==
    @Autowired
    public RestAccessDeniedHandler() {
        this.exceptionHandler = new FilterExceptionHandler();
    }

    @Override
    public void handle(HttpServletRequest request,
                                    HttpServletResponse response,
                                    AccessDeniedException exception) {
      exceptionHandler
                .handleUnauthorisedAccessException(request,response,exception);
    }
}
