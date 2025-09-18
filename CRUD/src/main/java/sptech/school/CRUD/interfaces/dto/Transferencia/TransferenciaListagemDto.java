package sptech.school.CRUD.interfaces.dto.Transferencia;

import lombok.*;

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
    private LocalDateTime ultimaMovimentacao;
    private Integer quantidadeTransferida;
}
