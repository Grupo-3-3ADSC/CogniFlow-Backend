package sptech.school.CRUD.interfaces.dto.OrdemDeCompra;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdemDeCompraCadastroDto {

    @NotBlank(message = "Prazo de entrega não pode ser nulo ou vazio")
    private String prazoEntrega;

    @NotBlank(message = "Condição de pagamento não pode ser nula ou vazia")
    private String condPagamento;

    @NotBlank(message = "Tipo de compra é obrigatório (UNIDADE ou QUILO)")
    private String tipoCompra;

    // Campos opcionais — validados conforme o tipoCompra
    private Double valorKg;

    private Double valorUnitario;

    @DecimalMin(value = "0.01", message = "Quantidade/Peso deve ser maior que zero")
    private Integer quantidade; // <- aceitando peso ou quantidade inteira

    @NotBlank(message = "Rastreabilidade não pode ser nula ou vazia")
    @Size(min = 7, max = 17)
    private String rastreabilidade;

    @NotBlank(message = "Descrição do material não pode ser nula ou vazia")
    @Size(min = 3, max = 100)
    private String descricaoMaterial;

    @NotNull(message = "Fornecedor não pode ser nulo")
    private Integer fornecedorId;

    @NotNull(message = "Estoque não pode ser nulo")
    private Integer estoqueId;

    @NotNull(message = "Usuário não pode ser nulo")
    private Integer usuarioId;
}
