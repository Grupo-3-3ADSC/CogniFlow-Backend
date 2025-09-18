package sptech.school.CRUD.infrastructure.adapter.Rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receiveMessage(Object message) {
        System.out.println("Mensagem recebida: " + message);
        // aqui você processa o conteúdo
    }
}
