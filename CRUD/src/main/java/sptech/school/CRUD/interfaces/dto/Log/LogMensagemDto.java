package sptech.school.CRUD.interfaces.dto.Log;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogMensagemDto {
    private Long id;
    private String mensagem;
    private LocalDateTime data;
}
