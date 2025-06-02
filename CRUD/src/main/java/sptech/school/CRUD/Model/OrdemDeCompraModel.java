package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OrdemDeCompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private FornecedorModel fornecedor;
    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private EstoqueModel estoque;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
}
