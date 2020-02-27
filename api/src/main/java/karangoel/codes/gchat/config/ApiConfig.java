package karangoel.codes.gchat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static karangoel.codes.gchat.util.Constants.SIGN_UP_URL;
import static karangoel.codes.gchat.util.Constants.WEB_SOCKET_URL;

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

    @Value("${http.getMap}")
    private String httpGetMap;



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

    @Bean
    public List<String> httpGetMap() {
        log.info("http get = {}", httpGetMap);
        List<String> gets = new ArrayList<>(Arrays.asList(httpGetMap.split(",")));
        gets.add(WEB_SOCKET_URL);
        return gets;
    }

    @Bean
    public List<String> httpPostMap() {
        log.info("http posts = {}", "");
        List<String> posts = new ArrayList<>();
        posts.add(SIGN_UP_URL);
        posts.add(WEB_SOCKET_URL);
        return posts;
    }

}
