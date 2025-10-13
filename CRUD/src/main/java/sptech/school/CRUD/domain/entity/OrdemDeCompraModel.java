package sptech.school.CRUD.domain.entity;

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
    private LocalDateTime dataDeEmissao;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean pendenciaAlterada;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", insertable = false, updatable = false)
    private FornecedorModel fornecedor;

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private UsuarioModel usuario;

    @OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOrdemCompraModel> itens = new ArrayList<>();

}

