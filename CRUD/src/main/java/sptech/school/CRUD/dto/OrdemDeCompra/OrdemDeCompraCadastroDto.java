package sptech.school.CRUD.dto.OrdemDeCompra;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdemDeCompraCadastroDto {
    @NotBlank(message = "Prazo de Entrega não pode ser nulo ou vazio")
    private String prazoEntrega;

    @NotBlank(message = "I.E não pode ser nulo ou vazio")
    @Size(min = 12, max = 12, message = "IE deve ter exatamente 12 caracteres")
    private String ie;

    @NotBlank(message = "Condição de Pagamento não pode ser nulo ou vazio")
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
    private String descricaoMaterial;

    @NotNull(message = "Valor Unitário não pode ser nulo ou vazio")
    @DecimalMin(value = "0.01", message = "Valor unitário deve ser maior que zero")
    private Double valorUnitario;

    @NotNull(message = "Quantidade não pode ser nulo ou vazio")
    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    private Integer quantidade;

    @NotNull(message = "IPI não pode ser nulo ou vazio")
    @DecimalMin(value = "0.00", message = "IPI deve ser zero ou maior")
    private Double ipi;

    @NotNull(message = "Fornecedor não pode ser nulo ou vazio")
    private Integer fornecedorId;

    @NotNull(message = "Estoque não pode ser nulo ou vazio")
    private Integer estoqueId;

    @NotNull(message = "Usuario não pode ser nulo ou vazio")
    private Integer usuarioId;
}