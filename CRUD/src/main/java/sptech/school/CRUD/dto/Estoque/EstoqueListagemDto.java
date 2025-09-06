package sptech.school.CRUD.dto.Estoque;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
