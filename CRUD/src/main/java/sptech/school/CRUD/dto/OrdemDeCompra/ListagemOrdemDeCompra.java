package sptech.school.CRUD.dto.OrdemDeCompra;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListagemOrdemDeCompra {
    private Integer id;
    private String prazoEntrega;
    private String ie;
    private String condPagamento;
    private Double valorKg;
    private String rastreabilidade;
    private Double valorPeca;
    private String descricaoMaterial;
    private Double valorUnitario;
    private Integer quantidade;
    private Double ipi;
    private Integer fornecedorId;
    private Integer estoqueId;
    private Integer usuarioId;
    private String nomeFornecedor;
    private String descricaoMaterialCompleta;
    private LocalDateTime dataDeEmissao;
    private Boolean pendenciaAlterada;
}
