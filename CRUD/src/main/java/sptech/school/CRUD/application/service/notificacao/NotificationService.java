package sptech.school.CRUD.application.service.notificacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.infrastructure.adapter.Rabbit.RabbitProducer;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RabbitProducer rabbitProducer;
    private final EmailService emailService;

    public void notificar(
            String tipoEvento, // Ex: "transferencia", "ordem_compra", etc.
            String status,     // Ex: "CRIADO", "ATUALIZADO"
            String idReferencia,
            String mensagemToast,
            String assuntoEmail,
            String mensagemEmail,
            String emailDestino
    ) {
        // Notifica o front via RabbitMQ
        rabbitProducer.sendEvent(tipoEvento, status, idReferencia, mensagemToast);

        // Envia e-mail detalhado
        emailService.enviarEmail(emailDestino, assuntoEmail, mensagemEmail);
}

}
