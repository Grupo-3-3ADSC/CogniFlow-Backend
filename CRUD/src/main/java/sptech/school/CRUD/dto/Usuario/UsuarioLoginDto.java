package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
public class UsuarioLoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
