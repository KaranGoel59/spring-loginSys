package karangoel.codes.gchat.config.security;

import karangoel.codes.gchat.config.exception.RestAccessDeniedHandler;
import karangoel.codes.gchat.config.exception.RestAuthenticationEntryPoint;
import karangoel.codes.gchat.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static karangoel.codes.gchat.util.Constants.SIGN_UP_URL;
import static karangoel.codes.gchat.util.Constants.WEB_SOCKET_URL;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // == fields ==
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private RestAccessDeniedHandler restAccessDeniedHandler;

    private List<String> httpHeaders;
    private List<String> httpOrigins;
    private List<String> httpMethods;
    private List<String> httpGetMap;
    private List<String> httpPostMap;
    private List<String> webIgnore;

    // == bean setters ==
    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void setRestAuthenticationEntryPoint(RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Autowired
    public void setRestAccessDeniedHandler(RestAccessDeniedHandler restAccessDeniedHandler) {
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    @Autowired
    public void setHeaders(List<String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @Autowired
    public void setOrigins(List<String> httpOrigins) {
        this.httpOrigins = httpOrigins;
    }

    @Autowired
    public void setMethods(List<String> httpMethods) {
        this.httpMethods = httpMethods;
    }

    @Autowired
    public void setHttpGetMap(List<String> httpGetMap) { this.httpGetMap = httpGetMap; }

    @Autowired
    public void setHttpPostMap(List<String> httpPostMap) { this.httpPostMap = httpPostMap; }

    @Autowired
    public void webIgnore(List<String> webIgnore) {
        this.webIgnore = webIgnore;
    }

    // == public methods ==
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .disable()

                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, httpPostMap.toArray(new String[0])).permitAll()
                    .antMatchers("/api/**").fullyAuthenticated()
                    .antMatchers(HttpMethod.GET, httpGetMap.toArray(new String[0])).permitAll()

                .anyRequest()
                    .authenticated()
                .and()
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new JWTAuthorizationFilter(authenticationManager()))

                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                    .accessDeniedHandler(restAccessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(webIgnore.toArray(new String[0]));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(httpHeaders);
        configuration.setAllowedOrigins(httpOrigins);
        configuration.setAllowedMethods(httpMethods);
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
