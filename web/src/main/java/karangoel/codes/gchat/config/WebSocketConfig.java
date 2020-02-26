package karangoel.codes.gchat.config;

import karangoel.codes.gchat.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static karangoel.codes.gchat.util.Mappings.WEB_SOCKET;
import static karangoel.codes.gchat.util.MessagePrefixes.APP_DESTINATION;
import static karangoel.codes.gchat.util.MessagePrefixes.GROUP_BROKER;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(WEB_SOCKET)
                .withSockJS()
                .setInterceptors(httpSessionHandshakeInterceptor());
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(APP_DESTINATION);
        registry.enableSimpleBroker(GROUP_BROKER);
    }

    @Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest) request;
                    HttpServletRequest servletRequest = servletServerRequest.getServletRequest();
                    Cookie token = WebUtils.getCookie(servletRequest, Constants.HEADER_STRING);

                    if(token != null) {
                        attributes.put("token", token.getValue());
                    } else {
                        return false;
                    }
                }
                return true;
            }
            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
            }
        };
    }
}
