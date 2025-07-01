package sptech.school.CRUD.dto.Estoque;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetirarEstoqueDto {


    private String tipoMaterial;
    private Integer quantidadeAtual;
    private String tipoTransferencia;
}
