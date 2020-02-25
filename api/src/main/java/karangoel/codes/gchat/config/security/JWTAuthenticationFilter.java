package karangoel.codes.gchat.config.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import karangoel.codes.gchat.config.exception.FilterExceptionHandler;
import karangoel.codes.gchat.model.UserAccount;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static karangoel.codes.gchat.util.Constants.*;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final FilterExceptionHandler exceptionHandler;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.exceptionHandler = new FilterExceptionHandler();
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(
                                                HttpServletRequest req,
                                                HttpServletResponse res
    ) throws AuthenticationException {
        Authentication authentication = null;
        try {
            UserAccount cred = new ObjectMapper()
                    .readValue(req.getInputStream(), UserAccount.class);

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            cred.getEmail(),
                            cred.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (BadCredentialsException ex) {
             exceptionHandler
                    .handleUnauthorisedAccessException(req,res,ex);
        }

        return  authentication;
    }

    @Override
    protected void successfulAuthentication(
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth
    ) {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        Cookie cookie = new Cookie(HEADER_STRING, TOKEN_PREFIX + token );
        cookie.setHttpOnly(true);
        res.addCookie(cookie);
    }

}
