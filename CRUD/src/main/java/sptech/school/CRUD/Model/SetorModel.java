package sptech.school.CRUD.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class SetorModel {

        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeSetor;
    private String descricao;

    @JoinColumn(name = "estoque_id")
    @ManyToOne
    private EstoqueModel estoque;

}
