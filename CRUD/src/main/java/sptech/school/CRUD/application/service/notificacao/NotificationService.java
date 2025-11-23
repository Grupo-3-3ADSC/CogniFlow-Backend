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
    private final EmailService emailService;
    private final UsuarioEmailRepository usuarioEmailRepository;

    public void notificar(NotificationType tipo, String idReferencia, String detalhes) {
        try {
            // 1️⃣ Envia APENAS UM evento WebSocket (broadcast para todos)
            rabbitProducer.sendEvent(
                    tipo.getEntity(),
                    tipo.getEventType(),
                    idReferencia,
                    tipo.getMensagemToast(),
                    null  // Sem email = broadcast
            );

            System.out.println("✅ [NOTIFICAÇÃO] " + tipo.name() + " #" + idReferencia);

            // 2️⃣ Envia emails individuais
            List<String> emailsDestino = usuarioEmailRepository.findAllEmails();

            System.out.println("📧 [EMAILS] Enviando para " + emailsDestino.size() + " usuários...");

            String mensagemCompleta = tipo.getMensagemEmailCompleta(idReferencia, detalhes);

            for (String email : emailsDestino) {
                try {
                    emailService.enviarEmail(
                            email,
                            tipo.getAssuntoEmail(),
                            mensagemCompleta
                    );
                    System.out.println("   ✓ " + email);
                } catch (Exception e) {
                    System.err.println("   ✗ Erro: " + email + " - " + e.getMessage());
                }
            }

            System.out.println("🎉 [CONCLUÍDO] 1 toast broadcast + " + emailsDestino.size() + " emails");

        } catch (Exception e) {
            System.err.println("❌ [ERRO CRÍTICO] " + tipo.name() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método legado (mantém compatibilidade com código existente)
     */
    public void notificar(
            String tipoEvento,
            String status,
            String idReferencia,
            String mensagemToast,
            String assuntoEmail,
            String mensagemEmail
    ) {
        try {
            rabbitProducer.sendEvent(tipoEvento, status, idReferencia, mensagemToast, null);

            List<String> emailsDestino = usuarioEmailRepository.findAllEmails();

            for (String email : emailsDestino) {
                try {
                    emailService.enviarEmail(email, assuntoEmail, mensagemEmail);
                } catch (Exception e) {
                    System.err.println("   ✗ Erro ao enviar para " + email);
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Erro ao notificar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Notificação simplificada sem emails (apenas WebSocket)
     */
    public void notificarSemEmail(NotificationType tipo, String idReferencia) {
        try {
            rabbitProducer.sendEvent(
                    tipo.getEntity(),
                    tipo.getEventType(),
                    idReferencia,
                    tipo.getMensagemToast(),
                    null
            );
            System.out.println("✅ [NOTIFICAÇÃO WS] " + tipo.name() + " #" + idReferencia);
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }

}
