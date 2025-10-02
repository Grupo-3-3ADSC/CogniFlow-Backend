package sptech.school.CRUD.infrastructure.adapter.WebSocket;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint que o front vai se conectar
        registry.addEndpoint("/ws-notifications").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // prefixo para mensagens enviadas do back para o front
        registry.enableSimpleBroker("/topic");
        // prefixo para mensagens enviadas do front para o back (se precisar)
        registry.setApplicationDestinationPrefixes("/app");
    }
}
