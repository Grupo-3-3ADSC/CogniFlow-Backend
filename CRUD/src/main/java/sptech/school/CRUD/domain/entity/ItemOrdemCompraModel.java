package sptech.school.CRUD.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class ItemOrdemCompraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Ordem de compra associada
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemDeCompraModel ordem;

    // Material (estoque) associado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estoque_id", nullable = false)
    private EstoqueModel estoque;

    private Integer quantidade;
    private Double valorUnitario;
    private Double ipi;
    private String descricaoMaterial;
    private Double valorKg;
    private Double valorPeca;
    private String rastreabilidade;
}

