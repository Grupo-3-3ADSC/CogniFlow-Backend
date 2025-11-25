package sptech.school.CRUD.infrastructure.adapter.Rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.application.service.notificacao.EmailService;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioEmailRepository;
import sptech.school.CRUD.interfaces.dto.Eventos.EventRequest;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioEmailRepository usuarioEmailRepository;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receiveMessage(DomainEvent event) {
        System.out.println("Evento recebido: " + event);

        // Se for evento de e-mail → envia em background
        if (event.getEventType() != null && event.getEventType().endsWith("_EMAIL")) {
            enviarEmailsAssincronos(event);
            return; // não duplica o toast
        }

        // Caso contrário → notificação normal (WebSocket)
        Map<String, Object> payload = new HashMap<>();
        payload.put("entity", event.getEntity());
        payload.put("mensagem", event.getMensagem());
        payload.put("entityId", event.getEntityId());
        payload.put("timestamp", event.getTimestamp());

        messagingTemplate.convertAndSend("/topic/notificacoes", payload);
    }

    private void enviarEmailsAssincronos(DomainEvent event) {
        try {
            String[] partes = event.getMensagem().split("\\|\\|\\|", 2);
            String assunto = partes[0];
            String corpo = partes.length > 1 ? partes[1] : "";

            var emails = usuarioEmailRepository.findAllEmails();

            emails.parallelStream().forEach(email -> {
                try {
                    emailService.enviarEmail(email, assunto, corpo);
                } catch (Exception e) {
                    System.err.println("Falha ao enviar e-mail para " + email + ": " + e.getMessage());
                }
            });

            System.out.println("E-mails enviados (" + emails.size() + ") para: " + event.getEntity() + " #" + event.getEntityId());
        } catch (Exception e) {
            System.err.println("ERRO no envio assíncrono de e-mails: " + e.getMessage());
        }
    }

}
