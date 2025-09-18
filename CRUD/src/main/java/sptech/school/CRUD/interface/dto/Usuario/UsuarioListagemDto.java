package sptech.school.CRUD.dto.Usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.CRUD.dto.Cargo.CargoListagemDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioListagemDto {

    private Integer id;
    private String nome;
    private String email;
    private CargoListagemDto cargo;
    private Boolean ativo;
}
