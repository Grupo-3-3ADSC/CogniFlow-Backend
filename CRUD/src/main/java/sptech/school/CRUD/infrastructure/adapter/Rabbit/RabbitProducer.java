package sptech.school.CRUD.infrastructure.adapter.Rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEvent(String entity, String eventType, String entityId, String mensagem, String emailDestino) {
        DomainEvent event = new DomainEvent(
                entity,
                eventType,
                entityId,
                LocalDateTime.now().toString(),
                mensagem,
                emailDestino
        );

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                event
        );

        System.out.println("Evento enviado: " + event);
    }
}
