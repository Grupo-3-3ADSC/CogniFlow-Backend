package sptech.school.CRUD.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioSenhaAtualizada {
    @NotBlank(message = "A senha não pode ser nulo ou vazio.")
    @Size(min = 6, message = "Precisa ter mais de 5 caracter")
    private String password;
}
