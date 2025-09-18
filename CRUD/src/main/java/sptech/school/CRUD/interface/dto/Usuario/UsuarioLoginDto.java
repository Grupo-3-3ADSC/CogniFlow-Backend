package sptech.school.CRUD.dto.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginDto {
    @NotBlank(message = "E-mail não pode ser nulo ou vazio.")
    @Schema(example = "john@doe.com")
    @Email(message = "Insira um e-mail válido")
    private String email;
    @NotBlank(message = "Senha não pode ser nulo ou vazio.")
    @Schema(example = "123456")
    @Size(min = 6)
    private String password;
}
