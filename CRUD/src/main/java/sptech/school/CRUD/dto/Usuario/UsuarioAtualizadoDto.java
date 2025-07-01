package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizadoDto {



    @NotNull
    private Integer id;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;
    @NotNull

    private CargoModel cargo;


}
