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

    @NotNull(message = "Estoque não pode ser nulo")
    private Integer estoqueId;

    @NotBlank(message = "Prazo de entrega não pode ser vazio")
    private String prazoEntrega;

    @NotBlank(message = "Condição de pagamento não pode ser vazia")
    private String condPagamento;

    @NotNull(message = "Valor por KG não pode ser nulo ou vazio")
    @DecimalMin(value = "0.01", message = "Valor por quilo deve ser maior que zero")
    private Double valorKg;

    @NotBlank(message = "Rastreabilidade não pode ser nulo ou vazio")
    @Size(min = 16, message = "Rastreabilidade deve ter no mínimo 16 caracteres")
    private String rastreabilidade;

    @NotNull(message = "Valor por Peça não pode ser nulo ou vazio")
    @DecimalMin(value = "0.01", message = "Valor por peça deve ser maior que zero")
    private Double valorPeca;

    @NotBlank(message = "Descrição do Material não pode ser nulo ou vazio")
    @Size(min = 3, max = 100, message = "Descrição do material deve ter entre 3 e 100 caracteres")
    private String descricaoMaterial;

    @NotNull(message = "Valor Unitário não pode ser nulo ou vazio")
    @DecimalMin(value = "0.01", message = "Valor unitário deve ser maior que zero")
    private Double valorUnitario;

    @NotNull(message = "Quantidade não pode ser nulo ou vazio")
    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    private Integer quantidade;
}
