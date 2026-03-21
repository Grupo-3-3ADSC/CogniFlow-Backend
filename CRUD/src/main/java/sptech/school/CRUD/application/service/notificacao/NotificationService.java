package sptech.school.CRUD.application.service.notificacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.CRUD.infrastructure.adapter.Rabbit.RabbitProducer;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioEmailRepository;
import sptech.school.CRUD.infrastructure.persistence.usuario.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RabbitProducer rabbitProducer;

    /**
     * Notificação completa: WebSocket (instantânea) + E-mail (background)
     */
    public void notificar(NotificationType tipo, String idReferencia, String detalhes) {
        try {
            // 1. Notificação em tempo real para todos online → super rápido
            rabbitProducer.sendEvent(
                    tipo.getEntity(),
                    tipo.getEventType(),
                    idReferencia,
                    tipo.getMensagemToast(),
                    null
            );

            System.out.println("[NOTIFICAÇÃO] " + tipo.name() + " #" + idReferencia);

            // 2. Agendamento do envio de e-mail em background
            String payloadEmail = tipo.getAssuntoEmail() + "|||" +
                    tipo.getMensagemEmailCompleta(idReferencia, detalhes);

            rabbitProducer.sendEvent(
                    tipo.getEntity(),
                    tipo.getEventType() + "_EMAIL",  // sufixo indica que é para envio de e-mail
                    idReferencia,
                    payloadEmail,
                    null
            );

        } catch (Exception e) {
            System.err.println("[ERRO CRÍTICO] Falha ao notificar " + tipo.name() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Apenas WebSocket (ex: ações que não precisam de e-mail)
     */
    public void notificarSemEmail(NotificationType tipo, String idReferencia) {
        rabbitProducer.sendEvent(
                tipo.getEntity(),
                tipo.getEventType(),
                idReferencia,
                tipo.getMensagemToast(),
                null
        );
        System.out.println("[NOTIFICAÇÃO WS] " + tipo.name() + " #" + idReferencia);
    }

    // Método legado (opcional: pode remover depois que todo código antigo for atualizado)
    public void notificar(String tipoEvento, String status, String idReferencia, String mensagemToast, String assuntoEmail, String mensagemEmail) {
        rabbitProducer.sendEvent(tipoEvento, status, idReferencia, mensagemToast, null);
        if (assuntoEmail != null && mensagemEmail != null) {
            rabbitProducer.sendEvent(tipoEvento, status + "_EMAIL", idReferencia, assuntoEmail + "|||" + mensagemEmail, null);
        }
    }

}
