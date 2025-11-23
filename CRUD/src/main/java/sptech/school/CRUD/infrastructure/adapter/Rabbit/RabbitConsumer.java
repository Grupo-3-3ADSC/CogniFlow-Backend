package sptech.school.CRUD.infrastructure.adapter.Rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.application.service.notificacao.EmailService;
import sptech.school.CRUD.interfaces.dto.Eventos.EventRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receiveMessage(DomainEvent event) {
        try {
            System.out.println("📩 Evento recebido: " + event);

            // Validação básica
            if (event == null || event.getEntity() == null) {
                System.err.println("⚠️ Evento inválido, ignorando...");
                return;
            }

            // Monta payload SIMPLIFICADO (sem emailDestino)
            Map<String, Object> payload = new HashMap<>();
            payload.put("entity", event.getEntity());
            payload.put("eventType", event.getEventType());
            payload.put("entityId", event.getEntityId());
            payload.put("timestamp", event.getTimestamp());
            payload.put("mensagem", event.getMensagem());
            // ❗ emailDestino NÃO é enviado pro frontend

            // Envia para WebSocket
            messagingTemplate.convertAndSend("/topic/notificacoes", payload);

            System.out.println("🔔 Notificação enviada: " + event.getEntity() + " #" + event.getEntityId());

        } catch (Exception e) {
            System.err.println("❌ ERRO no consumer: " + e.getMessage());
            e.printStackTrace();
            // ❗ NÃO relança exceção para evitar loop infinito
        }
    }


    }
