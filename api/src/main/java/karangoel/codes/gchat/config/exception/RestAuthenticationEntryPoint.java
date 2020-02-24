package karangoel.codes.gchat.config.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private FilterExceptionHandler exceptionHandler;

    // == constructors ==
    @Autowired
    public RestAuthenticationEntryPoint() {
        this.exceptionHandler = new FilterExceptionHandler();
    }

    @Override
    public void commence(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException exception)
            throws IOException, ServletException {
        exceptionHandler
                .handleUnauthorisedAccessException(request,response,exception);
    }
}
