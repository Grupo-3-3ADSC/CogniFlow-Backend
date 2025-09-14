package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class TransferenciaModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime ultimaMovimentacao;
    private Integer quantidadeTransferida;
    private String setor;

    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private EstoqueModel estoque;
}
