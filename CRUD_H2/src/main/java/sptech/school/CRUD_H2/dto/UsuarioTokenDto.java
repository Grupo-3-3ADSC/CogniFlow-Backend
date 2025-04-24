package sptech.school.CRUD_H2.dto;

import lombok.*;


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
}
