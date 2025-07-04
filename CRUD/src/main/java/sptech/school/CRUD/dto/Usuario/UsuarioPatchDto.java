package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import sptech.school.CRUD.Model.CargoModel;

@Getter
@Setter
public class UsuarioPatchDto {
    @NotBlank(message = "Nome não pode ser nulo ou vazio.")
    private String nome;
    @NotBlank(message = "E-mail não pode ser nulo ou vazio.")
    @Email(message = "O e-mail deve conter um '@' e um domínio válido.")
    private String email;
    @NotBlank
    private CargoModel cargo;
}
