package sptech.school.CRUD.infrastructure.adapter.Rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import sptech.school.CRUD.interfaces.dto.Eventos.EventRequest;

@Component
public class RabbitConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receiveMessage(Object message) {
        System.out.println("Mensagem recebida: " + message);

        // envia em tempo real para o front
        messagingTemplate.convertAndSend("/topic/notificacoes", message);
    }
}
