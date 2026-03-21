package sptech.school.CRUD.interfaces.dto.Estoque;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarInfoEstoqueDto {
    private Double ipi;
    private String tipoMaterial;
}
