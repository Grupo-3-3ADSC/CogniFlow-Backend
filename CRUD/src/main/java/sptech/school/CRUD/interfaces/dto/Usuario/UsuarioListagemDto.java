package sptech.school.CRUD.interfaces.dto.Usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.CRUD.interfaces.dto.Cargo.CargoListagemDto;

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
