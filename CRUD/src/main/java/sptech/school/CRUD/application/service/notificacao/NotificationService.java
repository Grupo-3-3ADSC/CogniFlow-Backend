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

    public void notificar(
            String tipoEvento,
            String status,
            String idReferencia,
            String mensagemToast,
            String assuntoEmail,
            String mensagemEmail
    ) {
        try {
            // ✅ 1. Envia APENAS UM evento WebSocket (FORA do loop)
            rabbitProducer.sendEvent(
                    tipoEvento,
                    status,
                    idReferencia,
                    mensagemToast,
                    null  // ❗ SEM email = apenas 1 notificação
            );

            System.out.println("✅ [NOTIFICAÇÃO] Evento WebSocket enviado: " + tipoEvento + " #" + idReferencia);

            // ✅ 2. Busca emails e envia (DENTRO do loop, mas SEM gerar eventos)
            List<String> emailsDestino = usuarioEmailRepository.findAllEmails();

            System.out.println("📧 [EMAILS] Enviando para " + emailsDestino.size() + " destinatários...");

            for (String email : emailsDestino) {
                try {
                    emailService.enviarEmail(email, assuntoEmail, mensagemEmail);
                    System.out.println("   ✓ Email enviado para: " + email);
                } catch (Exception e) {
                    System.err.println("   ✗ Erro ao enviar para " + email + ": " + e.getMessage());
                }
            }

            System.out.println("🎉 [CONCLUÍDO] 1 notificação WebSocket + " + emailsDestino.size() + " emails");

        } catch (Exception e) {
            System.err.println("❌ [ERRO CRÍTICO] NotificationService: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
