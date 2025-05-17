package sptech.school.CRUD.dto.materiaPrima;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MateriaPrimaListagemDto {

    private String tipoMaterial;
    private String descricao;
}
