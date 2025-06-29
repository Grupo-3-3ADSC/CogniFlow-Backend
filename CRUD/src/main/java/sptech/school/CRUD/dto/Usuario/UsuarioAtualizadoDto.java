package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizadoDto {



    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull

    private CargoModel cargo;


}
