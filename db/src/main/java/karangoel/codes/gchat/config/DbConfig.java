package karangoel.codes.gchat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@PropertySource("classpath:db.properties")
public class DbConfig {

    @Value("${encode.strength:10}")
    private int encodeStrength;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(encodeStrength);
    }
}
