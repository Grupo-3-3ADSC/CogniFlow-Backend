package sptech.school.CRUD.dto.OrdemDeCompra;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdemDeCompraCadastroDto {

    @NotBlank(message = "Prazo de Entrega não pode ser nulo ou vazio")
    private String prazoEntrega;

    @NotBlank(message = "I.E não pode ser nulo ou vazio")
    @Size(min = 5, max = 12, message = "IE deve ter entre 5 e 12 caracteres")
    private String ie;

    @NotBlank(message = "Condição de Pagamento não pode ser nulo ou vazio")
    private String condPagamento;

    @NotBlank(message = "Valor por KG não pode ser nulo ou vazio")
    @DecimalMin(value = "0.01", message = "Valor por quilo deve ser maior que zero")
    private Double valorKg;

    @NotBlank(message = "Rastreabilidade não pode ser nulo ou vazio")
    @Size(min = 5, message = "Rastreabilidade deve ter no mínimo 5 caracteres")
    private String rastreabilidade;

    @NotBlank(message = "Valor por Peça não pode ser nulo ou vazio")
    @DecimalMin(value = "0.01", message = "Valor por peça deve ser maior que zero")
    private Double valorPeca;

    @NotBlank(message = "Descrição do Material não pode ser nulo ou vazio")
    private String descricaoMaterial;

    @NotBlank(message = "Valor Unitário não pode ser nulo ou vazio")
    @DecimalMin(value = "0.01", message = "Valor unitário deve ser maior que zero")
    private Double valorUnitario;

    @NotBlank(message = "Quantidade não pode ser nulo ou vazio")
    @Min(value = 1, message = "Quantidade deve ser no mínimo 1")
    private Integer quantidade;

    @NotBlank(message = "IPI não pode ser nulo ou vazio")
    @DecimalMin(value = "0.00", message = "IPI deve ser zero ou maior")
    private Double ipi;

    @NotNull(message = "Fornecedor não pode ser nulo ou vazio")
    private Integer fornecedorId;

    @NotNull(message = "Estoque não pode ser nulo ou vazio")
    private Integer estoqueId;

    @NotNull(message = "Usuario  não pode ser nulo ou vazio")
    private Integer usuarioId;

}
