package sptech.school.CRUD.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
}
