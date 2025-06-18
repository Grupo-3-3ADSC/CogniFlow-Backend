package sptech.school.CRUD.dto.Usuario;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import sptech.school.CRUD.Model.CargoModel;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioTokenDto {

    private Long userId;
    private String nome;
    private String email;
    private String token;
    private CargoModel cargo;
}
