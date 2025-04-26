package sptech.school.CRUD_H2.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDeleteDto {


    private Integer id;
    private String nome;
    private String email;
    private String password;
}
