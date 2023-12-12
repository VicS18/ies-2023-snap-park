package snappark.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableWebSocketMessageBroker
public class SocketsConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/alerts").setAllowedOriginPatterns("*").withSockJS();
    }

    @PostConstruct
    public void init() {
        System.out.println("WebSocket configuration initialized.");
    }
}


