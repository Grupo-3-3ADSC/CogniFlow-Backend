package sptech.school.CRUD.interfaces.dto.OrdemDeCompra;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdemDeCompraCadastroDto {

    @NotNull(message = "Fornecedor não pode ser nulo")
    private Integer fornecedorId;

    @NotNull(message = "Usuário não pode ser nulo")
    private Integer usuarioId;

    @NotBlank(message = "Prazo de entrega não pode ser vazio")
    private String prazoEntrega;

    @NotBlank(message = "Condição de pagamento não pode ser vazia")
    private String condPagamento;

    @NotEmpty(message = "Deve haver pelo menos um item na ordem")
    private List<ItemOrdemCompraDto> itens;

}
