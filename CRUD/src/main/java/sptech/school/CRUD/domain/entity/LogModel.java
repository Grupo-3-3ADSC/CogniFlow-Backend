package sptech.school.CRUD.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LogModel {

    @Id
    private Long id;
    private String mensagem;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "fornecedor_id")
    private Integer fornecedorId;

    @Column(name = "estoque_id")
    private Integer estoqueId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", insertable = false, updatable = false)
    private FornecedorModel fornecedor;

    @ManyToOne
    @JoinColumn(name = "estoque_id", insertable = false, updatable = false)
    private FornecedorModel estoque;
}
