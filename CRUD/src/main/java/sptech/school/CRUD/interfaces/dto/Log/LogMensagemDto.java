package sptech.school.CRUD.interfaces.dto.Log;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogMensagemDto {
    private Long id;
    private String mensagem;
}
