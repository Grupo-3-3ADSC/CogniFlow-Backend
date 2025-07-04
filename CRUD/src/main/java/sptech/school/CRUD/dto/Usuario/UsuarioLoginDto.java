package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
public class UsuarioLoginDto {
    @NotBlank(message = "Email não pode ser nulo ou vazio.")
    private String email;
    @NotBlank(message = "Senha não pode ser nulo ou vazio.")
    private String password;
}
