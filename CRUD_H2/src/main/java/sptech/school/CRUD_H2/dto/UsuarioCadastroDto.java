package sptech.school.CRUD_H2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCadastroDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
