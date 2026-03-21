package sptech.school.CRUD.interfaces.dto.OrdemDeCompra;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MudarQuantidadeAtualDto {
    private Integer id;
    private Integer quantidade;
}
