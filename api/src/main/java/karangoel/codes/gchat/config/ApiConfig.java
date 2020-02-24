package karangoel.codes.gchat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@PropertySource("classpath:api.properties")
public class ApiConfig {

    // == fields ==
    @Value("${http.cors.headers}")
    private String httpHeaders;

    @Value("${http.cors.origins}")
    private String httpOrigins;

    @Value("${http.cors.methods}")
    private  String httpMethods;

    @Value("${web.ignore}")
    private String webIgnore;


    // == bean methods ==
    @Bean
    public List<String> httpHeaders() {
        log.info("httpHeaders = {}",httpHeaders);
        return Arrays.asList(httpHeaders.split(","));
    }

    @Bean
    public List<String> httpOrigins() {
        log.info("httpOrigins = {}",httpOrigins);
        return Arrays.asList(httpOrigins.split(","));
    }

    @Bean
    public List<String> httpMethods() {
        log.info("httpMethods = {}",httpMethods);
        return Arrays.asList(httpMethods.split(","));
    }

    @Bean
    public List<String> webIgnore() {
        log.info("web ignore = {}",webIgnore);
        return Arrays.asList(webIgnore.split(","));
    }

}
