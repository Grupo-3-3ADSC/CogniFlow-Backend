package sptech.school.CRUD.infrastructure.adapter.Rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.interfaces.dto.Eventos.EventRequest;

import java.util.Map;

@Service
public class RabbitConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receiveMessage(DomainEvent event) {
        Map<String, Object> payload = Map.of(
                "entity", event.getEntity(),
                "eventType", event.getEventType(),
                "entityId", event.getEntityId(),
                "timestamp", event.getTimestamp(),
                "mensagem", event.getMensagem()
        );
        messagingTemplate.convertAndSend("/topic/notificacoes", payload);
    }
}
