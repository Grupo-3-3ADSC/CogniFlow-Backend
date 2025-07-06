package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sptech.school.CRUD.dto.Cargo.CargoListagemDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioListagemDto {

    private Integer id;
    private String nome;
    private String email;
    private CargoListagemDto cargo;
    private Boolean ativo;
}
