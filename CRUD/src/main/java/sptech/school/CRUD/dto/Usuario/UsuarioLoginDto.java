package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
public class UsuarioLoginDto {
    @NotBlank(message = "E-mail não pode ser nulo ou vazio.")
    @Email(message = "Insira um e-mail válido")
    private String email;
    @NotBlank(message = "Senha não pode ser nulo ou vazio.")
    @Size(min = 6)
    private String password;
}
