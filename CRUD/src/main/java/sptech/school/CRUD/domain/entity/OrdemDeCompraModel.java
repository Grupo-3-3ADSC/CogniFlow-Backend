package sptech.school.CRUD.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
        import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class OrdemDeCompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String prazoEntrega;
    private String condPagamento;
    private Double valorKg;
    private String rastreabilidade;
    private Double valorPeca;
    private String descricaoMaterial;
    private Double valorUnitario;
    private Integer quantidade;
    private Double ipi;
    private Integer pendentes;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean pendenciaAlterada;
    private LocalDateTime dataDeEmissao;
    @Column(name = "fornecedor_id")
    private Integer fornecedorId;

    @Column(name = "estoque_id")
    private Integer estoqueId;

    @Column(name = "usuario_id")
    private Integer usuarioId;



    @ManyToOne
    @JoinColumn(name = "fornecedor_id", insertable = false, updatable = false)
    private FornecedorModel fornecedor;

    @ManyToOne
    @JoinColumn(name = "estoque_id", insertable = false, updatable = false)
    private EstoqueModel estoque;

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "conjunto_id")
    @JsonBackReference
    private ConjuntoOrdemDeCompraModel conjuntoOrdemDeCompra;


}

