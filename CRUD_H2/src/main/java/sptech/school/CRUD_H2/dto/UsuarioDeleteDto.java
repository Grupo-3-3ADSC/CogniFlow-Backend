package sptech.school.CRUD_H2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDeleteDto {


    private Integer id;
    private String nome;
    private String email;
    private String password;
}
