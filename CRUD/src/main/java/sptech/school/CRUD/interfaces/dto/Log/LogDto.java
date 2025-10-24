package sptech.school.CRUD.interfaces.dto.Log;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogDto {
    private Long id;
    private String mensagem;
    private Integer usuarioId;
    private Integer fornecedorId;
}
