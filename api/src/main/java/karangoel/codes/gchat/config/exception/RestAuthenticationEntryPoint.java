package karangoel.codes.gchat.config.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final FilterExceptionHandler exceptionHandler;

    // == constructors ==
    @Autowired
    public RestAuthenticationEntryPoint() {
        this.exceptionHandler = new FilterExceptionHandler();
    }

    @Override
    public void commence(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException exception) {
        exceptionHandler
                .handleUnauthorisedAccessException(request,response,exception);
    }
}
