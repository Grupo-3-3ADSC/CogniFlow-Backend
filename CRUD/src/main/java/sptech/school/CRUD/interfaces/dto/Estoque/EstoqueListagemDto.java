package sptech.school.CRUD.interfaces.dto.Estoque;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueListagemDto {

    private Integer id;
    private String tipoMaterial;
    private Integer quantidadeAtual;
    private Integer quantidadeMinima;
    private Integer quantidadeMaxima;
    private Double ipi;
    private String tipoTransferencia;
    private LocalDateTime ultimaMovimentacao;
}
