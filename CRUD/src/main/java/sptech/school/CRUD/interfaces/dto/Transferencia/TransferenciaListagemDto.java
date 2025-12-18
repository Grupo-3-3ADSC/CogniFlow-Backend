package sptech.school.CRUD.interfaces.dto.Transferencia;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaListagemDto {

    private Integer id;
    private String tipoMaterial;
    private String setor;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ultimaMovimentacao;
    private Integer quantidadeTransferida;
    private Boolean confirmada;
}
