package sptech.school.CRUD.dto.Estoque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarEstoqueDto {

    private String tipoMaterial;
    private Integer quantidadeAtual;


}
